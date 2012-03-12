package bb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bb.domain.Dictionary;
import bb.repository.DictionaryRepository;

@Service
public class DictionaryService {

	@Autowired
	DictionaryRepository dictionaryRepository;

	private static List<String> dictionary = null;

	public Dictionary findByValue(String value) {
		return dictionaryRepository.findByValue(value);
	}

	public Dictionary create(Dictionary t) {
		// TODO Auto-generated method stub
		return dictionaryRepository.create(t);
	}

	public Dictionary read(Long id) {
		// TODO Auto-generated method stub
		return dictionaryRepository.read(id);
	}

	public Dictionary update(Dictionary t) {
		// TODO Auto-generated method stub
		return dictionaryRepository.update(t);
	}

	public void delete(Dictionary t) {
		dictionaryRepository.delete(t);

	}

	public boolean valideWord(String word) {
		boolean resp = false;

		if (dictionary == null) {
			dictionary = dictionaryRepository.allValue();
		}

		if (dictionary.contains(word.toLowerCase())) {
			resp = true;
		}

		return resp;
	}

	public boolean valideText(String text, int percent) {
		boolean resp = false;

		int valideWordCount = 0;
		String[] textArray = text.split(" ");

		for (String s : textArray) {
			if (valideWord(s)) {
				valideWordCount++;
			}
		}

		double sec = textArray.length / 100;
		System.out.println(sec);
		System.out.println(valideWordCount);
		System.out.println(textArray.length);

		if ((valideWordCount > 0) && valideWordCount / sec >= percent) {
			resp = true;
		}

		return resp;
	}

}
