package com.event_management.management.service;

import com.event_management.management.model.Event;
import com.event_management.management.model.EventRegistration;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
//import org.apache.commons.csv.CSVPrinter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {

    public void writeEventToCsv(Writer writer, List<EventRegistration> registrations) throws IOException {
        try(CSVWriter csvWriter = new CSVWriter(writer)){
            String[] header = {"registrationId","Event name","User Name","Registration Date","Status"};
            csvWriter.writeNext(header);
            for(EventRegistration registration : registrations){
                String[] data = {
                        String.valueOf(registration.getEventId()),
                        String.valueOf(registration.getEventId().getName()),
                        String.valueOf(registration.getUserId().getUsername()),
                        String.valueOf(registration.getStatus())
                };
                csvWriter.writeNext(data);

            }

        }catch (Exception e){

        }
    }

    public List<String[]> getEventData() {
        return List.of(
                new String[]{"Event ID", "Name", "Description", "Category", "Location", "Visibility"},
                new String[]{"1", "Tech Conference", "A tech gathering", "Technology", "San Francisco", "Public"},
                new String[]{"2", "Music Fest", "Live music show", "Entertainment", "Los Angeles", "Private"},
                new String[]{"3", "Art Expo", "Modern art exhibition", "Art", "New York", "Public"}
        );
    }

    public void exportEventsToCsv(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"events.csv\"");

            PrintWriter writer = response.getWriter();
            CSVWriter csvWriter = new CSVWriter(writer);

            List<String[]> data = getEventData();

            csvWriter.writeAll(data);
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
