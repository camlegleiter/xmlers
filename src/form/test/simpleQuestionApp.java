package form.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import form.questions.CheckQuestion;
import form.questions.Question;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;
import form.visitors.HTMLVisitor;
import form.visitors.JSONVisitor;
import form.visitors.XMLVisitor;

public class simpleQuestionApp {

	public static void main(String[] args) {
		
		//Create questions
		TextQuestion textq = new TextQuestion(1, 1, "What's your name?", 5);
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		RadioQuestion radioq = new RadioQuestion(2, 2, "Sex: ", answers);
		CheckQuestion checkq = new CheckQuestion(3, 3, "Sex: ", answers);
		SelectQuestion selectq = new SelectQuestion(4, 4, "Sex: ", answers);
		
		//Add questions to question list
		ArrayList<Question<?>> formqs = new ArrayList<Question<?>>();
		formqs.add(textq);
		formqs.add(radioq);
		
		//Prepare string to be printed
		String rtrnHTML = "";
		String rtrnXML = "";
		String rtrnJSON = "";
		HTMLVisitor htmlVisitor = new HTMLVisitor();
		XMLVisitor xmlVisitor = new XMLVisitor();
		JSONVisitor jsonVisitor = new JSONVisitor();

//		for(Question q: formqs){
//			rtrn += htmlVisitor.visit(q);
//		}
		rtrnHTML += htmlVisitor.visit(textq);
		rtrnHTML += htmlVisitor.visit(radioq);
		rtrnHTML += htmlVisitor.visit(selectq);
		rtrnHTML += htmlVisitor.visit(checkq);
		rtrnXML += xmlVisitor.visit(textq);
		rtrnXML += xmlVisitor.visit(radioq);
		rtrnXML += xmlVisitor.visit(selectq);
		rtrnXML += xmlVisitor.visit(checkq);
		rtrnJSON += jsonVisitor.visit(textq);
		rtrnJSON += jsonVisitor.visit(radioq);
		rtrnJSON += jsonVisitor.visit(selectq);
		rtrnJSON += jsonVisitor.visit(checkq);
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		//Preview the result
		try {
			 
		      File fileHTML = new File("C:\\Users\\Public\\Documents\\formTestHTML.html");
		      File fileXML = new File("C:\\Users\\Public\\Documents\\formTestXML.xml");
		      File fileJSON = new File("C:\\Users\\Public\\Documents\\formTestJSON.html");
		      
		      
		      BufferedWriter bwHTML = new BufferedWriter(new FileWriter(fileHTML));
		      BufferedWriter bwXML = new BufferedWriter(new FileWriter(fileXML));
		      BufferedWriter bwJSON = new BufferedWriter(new FileWriter(fileJSON));
		      bwHTML.write(rtrnHTML);
		      bwXML.write(rtrnXML);
		      bwJSON.write(rtrnJSON);
		      bwHTML.close();
		      bwXML.close();
		      bwJSON.close();
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
	}

}
