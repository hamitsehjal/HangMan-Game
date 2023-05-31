
/**********************************************
 *  Workshop 5
 *  Course:<JAC444> - 
 *  Semester - 4 Last Name:<SEHJAL> 
 *  First Name:<HAMIT> 
 *  ID:<139238208> Section:<ZAA> 
 *  This assignment represents my own work in accordance with Seneca Academic Policy.
 *  Signature Date:<2023-03-17> 
 *  **********************************************/

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {

	public ArrayList<String> listOfWords;

	public String displayHeader(ArrayList<String> letters) {
		String header = null;
		header = "\n(GUESS) Enter the letter in the word: ";
		for (String letter : letters) {
			header += letter;
		}
		header += " >";

		System.out.println(header);
		return header;
	}

	public HangmanGame(File sourceFile) throws FileNotFoundException {
		String line, words[] = null;
		ArrayList<String> listOfWords = new ArrayList<String>();
		try (Scanner input = new Scanner(sourceFile);) {
			while (input.hasNextLine()) {
				line = input.nextLine();
				words = line.split("\\s+");
				for (String word : words) {
					listOfWords.add(word);
				}
			}
			this.listOfWords = listOfWords;

		}

	}

	String randomWordPicker(ArrayList<String> words) {

		// generate a random whole number between 0 and size of array "words" using
		// Random class
		Random randomNumber = new Random();
		String word = null;
		int index = 0;

//		
		// for picking a word of atleast 4 letters
		do {
			index = randomNumber.nextInt(words.size());
			word = words.get(index);
		} while (word.length() < 4);

		return word;

	}

	public void rightLetter(ArrayList<String> userGuesses, String word, ArrayList<String> guessMe, int[] numOfGuess,
			String input, StringBuilder msg) {
		boolean isEntered = false;
		for (String myGuess : userGuesses) {
			if (myGuess.contains(input)) {
				isEntered = true;
				break;
			}
		}

		if (isEntered) {
			if (msg.length() == 0) {
				msg.append("entered a right guess again: \\\"\"" + input + "\" already in the word\"");
			} else {

				msg.replace(0, msg.length(),
						"entered a right guess again: \\\"\"" + input + "\" already in the word\"");
			}
		} else {
			// add user input to the list of "user Guesses"
			userGuesses.add(input);

			// Find the index at which the right guess occurs and update the output
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == input.charAt(0)) {
					guessMe.set(i, input);
					numOfGuess[0]--;
				}
			}
		}

	}

	void wrongLetter(ArrayList<String> userGuesses, String word, ArrayList<String> guessMe, int[] numOfGuess,
			String input, StringBuilder msg) {
		boolean isEntered = false;
		for (String myGuess : userGuesses) {
			if (myGuess.contains(input)) {
				isEntered = true;
				break;
			}
		}
		if (isEntered) {
			if (msg.length() == 0) {
				msg.append("entered a wrong guess again: \"You have already tried " + input + ", try a new letter\"");
			} else {

				msg.replace(0, msg.length(),
						"entered a wrong guess again: \"You have already tried " + input + ", try a new letter\"");
			}
		} else {
			if (msg.length() == 0) {
				msg.append("entered a wrong guess: \"," + input + " is not in the word\"");
			} else {
				msg.replace(0, msg.length(), "entered a wrong guess: \"," + input + " is not in the word\"");

			}
			numOfGuess[0]++;
			// add user input to the list of "user Guesses"
			userGuesses.add(input);

		}
	}

}
