#include "user.h"


    void User::setNom(std::string _nom){
        this->nom = _nom;
    }
    std::string User::getNom(){
        return this->nom;
    }
    void User::setAge(int age){
        this->age = age;
    }
    int User::getAge(){
        return this->age;
    }
    User::User(std::string nom, int age){
        this->nom = nom;
        this->age = age;
    }
    User::~User(){
        std::cout << "user détruit ; from ~User()" <<std::endl;
    }