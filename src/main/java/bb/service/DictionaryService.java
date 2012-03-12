package bb.service;

import java.util.ArrayList;
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

		for (String dic : dictionary) {
			if (dic.length() > 2 && word.startsWith(dic)) {
				resp = true;
				break;
			}
		}

		return resp;
	}

	public boolean valideText(String text, int percent) {
		boolean resp = false;

		int valideWordCount = 0;
		String[] textArray = text.split(" ");

		//legalabb 3 karakter hosszu legyen a szo es ne http-vel kezdodjon
		List<String> parsedWord = new ArrayList<String>();

		for (String s : textArray) {
			if (s.length() >= 3 && !s.startsWith("http") && !s.startsWith("@")) {
				parsedWord.add(s);
			}
		}

		for (String s : parsedWord) {
			if (valideWord(s)) {
				valideWordCount++;
			}
		}

		if (parsedWord.size() > 1) {

			double sec = parsedWord.size() / 100d;
			System.out.println(sec);
			System.out.println(valideWordCount);
			System.out.println(parsedWord.size());
			System.out.println(valideWordCount / sec);

			if ((valideWordCount > 0) && valideWordCount / sec >= percent) {
				resp = true;
			}
		}

		return resp;
	}

}
