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
		String rtrn = "";
		HTMLVisitor htmlVisitor = new HTMLVisitor();
//		for(Question q: formqs){
//			rtrn += htmlVisitor.visit(q);
//		}
		rtrn += htmlVisitor.visit(textq);
		rtrn += htmlVisitor.visit(radioq);
		rtrn += htmlVisitor.visit(selectq);
		
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		//Preview the result
		try {
			 
		      File file = new File("C:\\Users\\Public\\Documents\\simpleQuestion1.html");
		      
		      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		      bw.write(rtrn);
		      bw.close();
		      
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
	}

}
