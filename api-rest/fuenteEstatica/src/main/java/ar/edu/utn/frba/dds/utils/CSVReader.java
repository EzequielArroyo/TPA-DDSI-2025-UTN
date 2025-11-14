package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.entities.HechoCsvData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component("csv")
public class CSVReader implements Reader {

  @Override
  public List<HechoCsvData> leer(String rutaArchivo) {
    try (FileReader reader = new FileReader(rutaArchivo)) {
      CsvToBean<HechoCsvData> csvToBean = new CsvToBeanBuilder<HechoCsvData>(reader)
              .withType(HechoCsvData.class)
              .withSeparator(',')
              .withIgnoreQuotations(false)
              .withSkipLines(0)
              .build();
      return csvToBean.parse();
    } catch (IOException e) {
        System.out.println("Error al leer el archivo CSV: " + e.getMessage());
      return null;
    }
  }
}
