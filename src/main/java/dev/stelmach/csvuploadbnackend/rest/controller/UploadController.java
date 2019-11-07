package dev.stelmach.csvuploadbnackend.rest.controller;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import dev.stelmach.csvuploadbnackend.helper.ParseHelper;
import dev.stelmach.csvuploadbnackend.model.Person;
import dev.stelmach.csvuploadbnackend.model.PersonDTO;
import dev.stelmach.csvuploadbnackend.rest.ParsingResponse;
import dev.stelmach.csvuploadbnackend.service.PersonService;
import dev.stelmach.csvuploadbnackend.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.version}")
public class UploadController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private ValidationService validationService;
	private PersonService personService;

	public UploadController(ValidationService validationService, PersonService personService) {
		this.validationService = validationService;
		this.personService = personService;
	}

	@PostMapping("/upload")
	public ResponseEntity<ParsingResponse> uploadCSV(@RequestParam("file") MultipartFile file) throws IOException {
		InputStream inputStream = new BufferedInputStream(file.getInputStream());
		List<PersonDTO> validPersonDTOList = new ArrayList<>();
		List<PersonDTO> invalidPersonDTOList = new ArrayList<>();
		CsvParserSettings settings = new CsvParserSettings();
		settings.setDelimiterDetectionEnabled(true);
		settings.setNullValue("null");
		settings.setEmptyValue("empty");
		CsvParser parser = new CsvParser(settings);
		List<Record> records = parser.parseAllRecords(inputStream);
		// Start iteration from second element to ignore column definitions
		for (int i = 1; i < records.size(); i++) {
			Record record = records.get(i);
			PersonDTO personDTO = ParseHelper.convertRecordToPersonDTO(record);
			List<String> validationMessages = validationService.validatePersonDTO(personDTO);
			personDTO.setParsingMessages(validationMessages);
			if (validationMessages.isEmpty()) {
				Person person = ParseHelper.convertPersonDTOtoPerson(personDTO);
				personService.savePerson(person);
				personDTO.getParsingMessages().add("OK");
				validPersonDTOList.add(personDTO);
			} else {
				invalidPersonDTOList.add(personDTO);
			}
		}
		if (log.isDebugEnabled()) {
			String logMsg = "Successfully parsed %s entries. Errors in %s entries.";
			log.debug(logMsg, validPersonDTOList.size(), invalidPersonDTOList.size());
		}
		return new ResponseEntity<>(new ParsingResponse(validPersonDTOList.size(), invalidPersonDTOList.size(), validPersonDTOList, invalidPersonDTOList), HttpStatus.OK);
	}


}