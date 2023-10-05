package yonder.weather.howIsTheWeather.service;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import yonder.weather.howIsTheWeather.models.Result;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CsvWriterService {
    public void writeDataToCSV(List<Result> resultList) throws IOException {
        try(CSVWriter csvWriter = new CSVWriter(new FileWriter("data.csv"))) {
            csvWriter.writeNext(new String[]{"Name", "Temperature", "Wind"});

            for (Result result : resultList) {
                csvWriter.writeNext(new String[]{result.getName(), result.getTemperature(), result.getWind()});
            }
        }
    }
}
