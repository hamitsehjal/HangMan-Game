
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application implements EventHandler<ActionEvent> {
	private File sourceFile;
	private TextField text;
	private TextField userInput;
	private String randomWord;
	private Label l1;
	private Label errors;
	private Scene scene1;
	private Scene scene2;
	private Button b1;
	private Button b2;
	private Button startAgain;
	private Button endGame;
	private Stage stage;
	private HangmanGame game;
	private int[] wrongGuesses = { 0 };
	private int[] rightGuesses = { 0 };
	private ArrayList<String> guessMe;
	private ArrayList<String> userGuesses;
	private StringBuilder errorMessage;

	public void handleButton1() {

		if (rightGuesses[0] > 1) {

			String letter = text.getText();
			errors.setText("");
			errorMessage.replace(0, errorMessage.length(), "");
			if (randomWord.contains(letter)) {
				game.rightLetter(userGuesses, randomWord, guessMe, rightGuesses, letter, errorMessage);

			} else {
				// wrong guess
				game.wrongLetter(userGuesses, randomWord, guessMe, wrongGuesses, letter, errorMessage);

			}
			text.setText("");
			l1.setText(game.displayHeader(guessMe));
			if (errorMessage != null)
				errors.setText(errorMessage.toString());
		} else {
			text.setText("");
			this.changeToScene2();
		}
	}

	public void changeToFinalScene() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
			Platform.exit();
		}));

		Label thankyou = new Label("Thanks a Lot!");
		thankyou.setFont(Font.font("Arial", FontWeight.BOLD, 60));
		thankyou.setTextFill(Color.web("#fff"));

		Label congrats = new Label("Congratulations!");
		congrats.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		congrats.setTextFill(Color.web("#fff"));

		Image image = new Image("back.jpg");

		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));

		VBox vbox = new VBox(congrats, thankyou);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(40);
		vbox.setPadding(new Insets(100));
		vbox.setBackground(new Background(backgroundImage));

		Scene scene2 = new Scene(vbox, 800, 600);

		stage.setTitle("Congratulations!");
		stage.setScene(scene2);
		stage.show();
		timeline.play();

	}

	public void handleButton2() throws IOException {
		try (PrintWriter output = new PrintWriter(new FileOutputStream(this.sourceFile, true))) {

			String word = userInput.getText();
			output.append("\n" + word);

			startAgain = new Button("START AGAIN");
			startAgain.setOnAction(this);
			startAgain.getStyleClass().add("button-primary");

			endGame = new Button("END GAME");
			endGame.setOnAction(this);
			endGame.getStyleClass().add("button-danger");

			HBox hbox = new HBox(startAgain, endGame);
			hbox.setAlignment(Pos.CENTER);
			hbox.setSpacing(20);

			Scene scene2 = new Scene(hbox, 600, 600);
			scene2.getStylesheets().add("application.css"); // add CSS stylesheet to the scene

			stage.setScene(scene2);
			stage.show();

		}
	}

	@Override
	public void handle(ActionEvent event) {

		if (event.getSource() == b1) {
			handleButton1();
		} else if (event.getSource() == b2) {
			try {
				handleButton2();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (event.getSource() == startAgain) {
			changeToScene1();
		} else if (event.getSource() == endGame) {
			changeToFinalScene();
		}

	}

	public void changeToScene2() {

		VBox vbox = new VBox();

		Label myLabel = new Label("The word is " + randomWord + ". You made " + wrongGuesses[0] + " wrong guesses");
		myLabel.setAlignment(Pos.CENTER);
		myLabel.setFont(new Font(24));

		Label newWord = new Label("Please enter a new word to add to the file:");
		newWord.setAlignment(Pos.CENTER);
		newWord.setFont(new Font(16));

		userInput = new TextField();
		userInput.setPrefWidth(newWord.getWidth());

		b2 = new Button("Submit");
		b2.setStyle("-fx-background-color: #4CAF50; -fx-padding: 8px 16px; -fx-text-fill: white;");
		b2.setOnAction(this);

		vbox.getChildren().addAll(myLabel, newWord, userInput, b2);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(20);
		vbox.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 50px;");

		scene2 = new Scene(vbox, 800, 600);
		stage.setScene(scene2);
		stage.show();

	}

	public void changeToScene1() {

		// for generating a random word
		this.randomWord = game.randomWordPicker(game.listOfWords);
		System.out.println(this.randomWord);
		// Declarations and initializations
		guessMe = new ArrayList<String>(); // To store the string that displays the word as
											// user keeps on guessing it
		userGuesses = new ArrayList<String>(); // To store the user inputs
		// To keep record of number of wrong and right guesses
		// Since, I will be passing these values to functions, so using arrays
		errorMessage = new StringBuilder();
		rightGuesses[0] = this.randomWord.length();

		// initializing the ArrayList "guessMe"
		for (int i = 0; i < this.randomWord.length(); i++) {
			guessMe.add("*");
		}

		try {
			this.stage = new Stage();

			HBox hbox1 = new HBox();
			HBox hbox2 = new HBox();
			HBox hbox3 = new HBox();
			hbox1.setAlignment(Pos.CENTER);
			hbox2.setAlignment(Pos.CENTER);
			hbox3.setAlignment(Pos.CENTER);
			hbox1.setSpacing(10);

			// Create label, text field, and submit button for user input
			errors = new Label("");
			errors.setId("errors-label");

			l1 = new Label(this.game.displayHeader(guessMe));
			l1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			l1.setTextFill(Color.web("#0076a3"));
			text = new TextField();
			text.setPrefSize(35, 30);
			b1 = new Button("Guess");
			b1.setId("button-b1");
			b1.setOnAction(this);

			Label title = new Label("Welcome to Hangman's Game!!");
			title.setFont(new Font("Comic Sans", 36));
			title.setAlignment(Pos.CENTER);
			title.setPadding(new Insets(50, 0, 50, 0));

			hbox1.getChildren().addAll(l1, text, b1);
			hbox2.getChildren().add(title);
			hbox3.getChildren().add(errors);

			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(20);
			vbox.setPadding(new Insets(50));
			vbox.setStyle("-fx-background-color: #f2f2f2;");

			vbox.getChildren().add(hbox2);
			vbox.getChildren().add(hbox1);
			vbox.getChildren().add(hbox3);

			scene1 = new Scene(vbox, 800, 600);
			scene1.getStylesheets().add("application.css");
			stage.setScene(scene1);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parameters params = getParameters();
		List<String> args = params.getRaw();
		String filename = args.get(0);

		this.sourceFile = new File("src/" + filename);
		if (!sourceFile.exists()) {
			System.out.println("Source File " + sourceFile.getCanonicalPath() + " does not exists");
			System.exit(1);
		}

		this.game = new HangmanGame(sourceFile);
		this.changeToScene1();

	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}

}
