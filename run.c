#include<stdio.h>
int main()
{
	system("javac DataPreprocess.java");
	system("java DataPreprocess");
	system("javac Fagin.java");
	system("java Fagin");
	system("rm *.class input original");
	return (0);
}
