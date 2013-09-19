package form.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import form.questions.Question;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;
import form.visitors.HTMLVisitor;
import form.visitors.XMLVisitor;

public class simpleQuestionApp {

	public static void main(String[] args) {
		
		//Create questions
		TextQuestion textq = new TextQuestion("first", 1, "What's your name?", 5);
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		RadioQuestion radioq = new RadioQuestion("second", 2, "Sex: ", answers);
		SelectQuestion selectq = new SelectQuestion("third", 3, "Sex: ", answers);
		
		//Add questions to question list
		ArrayList<Question> formqs = new ArrayList<Question>();
		formqs.add(textq);
		formqs.add(radioq);
		
		//Prepare string to be printed
		String rtrnHTML = "";
		String rtrnXML = "";
		HTMLVisitor htmlVisitor = new HTMLVisitor();
		XMLVisitor xmlVisitor = new XMLVisitor();
//		for(Question q: formqs){
//			rtrn += htmlVisitor.visit(q);
//		}
		rtrnHTML += htmlVisitor.visit(textq);
		rtrnHTML += htmlVisitor.visit(radioq);
		rtrnHTML += htmlVisitor.visit(selectq);
		rtrnXML += xmlVisitor.visit(textq);
		rtrnXML += xmlVisitor.visit(radioq);
		rtrnXML += xmlVisitor.visit(selectq);
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		//Preview the result
		try {
			 
		      File fileHTML = new File("C:\\Users\\Public\\Documents\\formTestHTML.html");
		      File fileXML = new File("C:\\Users\\Public\\Documents\\formTestXML.xml");
		      
		      
		      BufferedWriter bwHTML = new BufferedWriter(new FileWriter(fileHTML));
		      BufferedWriter bwXML = new BufferedWriter(new FileWriter(fileXML));
		      bwHTML.write(rtrnHTML);
		      bwXML.write(rtrnXML);
		      bwHTML.close();
		      bwXML.close();
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
	}

}
