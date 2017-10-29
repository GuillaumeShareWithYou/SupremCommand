#include <iostream>
#include "user.h"
using namespace std;

int main(int argc, char *argv[]){
    
    string nom = argv[1];
    int age = std::stoi(argv[2]);
    User u(nom,age);
    cout <<"Bonjour " << u.getNom() << " qui a " << u.getAge() << "ans" << endl;
   
    return 0;
}