package com.pavel.sumtimes.springoutput;

import com.pavel.sumtimes.springoutput.dao.SumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class OutputController {

    private final SumDao sumDao;
    private final Cache cache;
    @Value("${app.quantum}")
    private long quantumMs;

    @Autowired
    public OutputController(SumDao sumDao, Cache cache) {
        this.sumDao = sumDao;
        this.cache = cache;
    }

    @RequestMapping(value = "output", method = RequestMethod.GET)
    public String getRequest(){
        return "output";
    }

    @RequestMapping(value = "output", method = RequestMethod.POST)
    public ModelAndView getResults(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                               LocalDateTime min, @RequestParam("endDate") @DateTimeFormat(
                                                       iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime max) {
        if (min == null || max == null) {
            return new ModelAndView("output", Map.of("error", true));
        }
        Timestamp minTime = Timestamp.valueOf(min);
        Timestamp maxTime = Timestamp.valueOf(max);
        Timestamp lastRecordTime = cache.getLastAcquiredQuantum();

        if (lastRecordTime.before(minTime)) {
            return new ModelAndView("output", Map.of("emptyResults", true));
        }

        maxTime.setTime(Math.min(lastRecordTime.getTime()-quantumMs, maxTime.getTime()));
        Map<Timestamp, Long> sums = cache.get(minTime, maxTime);
        if (sums == null) {// if cache not in range of requested times
           sums = sumDao.getInRange(minTime, maxTime);
        }
        if  (sums.isEmpty()) {
            return new ModelAndView("output", Map.of("emptyResults", true));
        }

        List<ResultSum> resultSumList = new ArrayList<>();
        for (long i = minTime.getTime(); i <= maxTime.getTime(); i = i + quantumMs) {
            resultSumList.add(new ResultSum(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .format(new Timestamp(i).toLocalDateTime()), sums.getOrDefault(new Timestamp(i), (long) 0)));
        }

        return new ModelAndView("output", Map.of("sum", resultSumList));
    }

    private static class ResultSum {
        private final String date;
        private final long sum;

        public ResultSum(String date, long sum) {
            this.date = date;
            this.sum = sum;
        }

        public String getDate() {
            return date;
        }

        public long getSum() {
            return sum;
        }
    }
}
