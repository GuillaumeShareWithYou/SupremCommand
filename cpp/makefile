all: main.exe

main.exe: main.o user.o
	g++ -g main.o user.o -o main.exe
user.o : user.cpp user.h
	g++ -c user.cpp
main.o : main.cpp user.h
	g++ -c main.cpp