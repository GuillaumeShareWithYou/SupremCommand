#ifndef USER_H;
#define USER_H;
#include <iostream>

class User{

public:
    void setNom(std::string nom);
    std::string getNom();
    void setAge(int age);
    int getAge();
    User(std::string nom, int age);
    ~User();
private:
    std::string nom;
    int age;


};


#endif;