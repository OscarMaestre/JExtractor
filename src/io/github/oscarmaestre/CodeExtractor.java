package io.github.oscarmaestre;

import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.DumpVisitor;



public class CodeExtractor {
	public static void main(String[] args) throws IOException {
		 // creates an input stream for the file to be parsed
       FileInputStream in = new FileInputStream(args[0]);
       String className= args[1];
       CompilationUnit cu;
       try {
           // parse the file
           cu = JavaParser.parse(in);
       } finally {
           in.close();
       }
       if (args[2].equals("class")){
    	   ClassFinderVisitor v=new ClassFinderVisitor(className);
           // prints the resulting compilation unit to default system output
           v.visit(cu, null);
           return ;
       } 
       if (args[2].equals("method")){
    	   MethodFinderVisitor v=new MethodFinderVisitor(className);
           // prints the resulting compilation unit to default system output
           v.visit(cu, null);
           return ;
       }
       
       
	}
	private static class ClassFinderVisitor extends DumpVisitor{
		private String className;
		public ClassFinderVisitor(String className) {
			this.className=className;
		}
		
		@Override
		public void visit(ClassOrInterfaceDeclaration _class, Object arg1) {
			super.visit(_class, arg1);
			String nameFound=_class.getName();
			//System.out.println(nameFound);
			if (nameFound.equals(  className )){
				System.out.println(_class.toString());
			}
		}
	}
	private static class MethodFinderVisitor extends DumpVisitor{
		private String methodName;
		public MethodFinderVisitor(String className) {
			this.methodName=className;
		}
		
		@Override
		public void visit(MethodDeclaration method, Object arg1) {
			super.visit(method, arg1);
			String nameFound=method.getName();
			//System.out.println(nameFound);
			if (nameFound.equals(  methodName )){
				System.out.println(method.toString());
			}
		}
	}
		
}