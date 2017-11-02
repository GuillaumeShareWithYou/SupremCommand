#include <iostream>
#include "user.h"
#include <chrono>
#include <thread>
using namespace std;

int main(int argc, char *argv[]){
    
    string nom = argv[1];
    int n = std::stoi(argv[2]);
    
    cout <<"Bonjour " << nom << " qui veut " << n << " lignes"<< endl;
   cout << "Je test les thread sur plusieurs lignes" << endl;
for(int i=0; i<n; i++){
cout << "Je test les thread sur plusieurs lignes" << i << endl;
}


    return 0;
}

