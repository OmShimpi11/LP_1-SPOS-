//------------------------DLL--------------------------

//********* testJni1.java ************
public class testJni1
{
static{
System.loadLibrary("native");
}
public static void main(String args[])
{
System.out.println("Addition is "+ new testJni1().add(10,20));
}
private native int add(int n1,int n2);
}


/******* Compiling the file ********
Command:-
gescoe@gescoe-optiplex-3020:~/Documents/AY21-22/LP-1$ javac -h . testJni1.java


********** testJni1.c **********/
#include<jni.h>
#include<stdio.h>
#include "testJni1.h" JNIEXPORT jint JNICALL Java_testJni1_add
(JNIEnv *env, jobject thisobj, jint n1, jint n2)
{
jint res;
res=n1+n2;
return res;
}
 
 
/******** Compiling the file *********
Command :-
gescoe@gescoe-optiplex-3020:~/Documents/AY21-22/LP-1$ gcc -c -fPIC -I
/usr/lib/jvm/java-1.8.0-openjdk-amd64/include
-I/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux testJni1.c -o testJni1.o

***** After That RUn These Two Commands *******

gescoe@gescoe-optiplex-3020:~/Documents/AY21-22/LP-1$ gcc -shared -fPIC -o
libnative.so testJni1.o -lc

gescoe@gescoe-optiplex-3020:~/Documents/AY21-22/LP-1$ java -Djava.library.path=. testJni1

*/

