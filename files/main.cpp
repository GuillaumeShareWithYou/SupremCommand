#include <thread>
#include <vector>
#include <set>
#include <thread>
#include <map>
#include "Bateau.h"
#include "Kata.h"
#include <algorithm>
using namespace std;

map<char,char> m = {
        {'A','T'},
        {'T','A'},
        {'G','C'},
        {'C','G'},
};
string DNAStrand(string dna) {
    string adn;
    for (char c : dna) {
        adn.push_back(m.at(c));
    }

    return adn;
}
bool myFunction(string s1, string s2)
{
    return (s1.length() < s2.length());
}
std::vector<std::string> sortByLength(std::vector<std::string> array)
{
    std::sort(array.begin(),array.end(),myFunction);
    return array;
}
bool f(int a, int b)
{
    return (a > b);
}
bool fs(string s1, string s2)
{
    return s1.length()<s2.length();
}
std::vector<int> sqInRect(int lng, int wdth){
    if(lng == wdth) return {};
    std::vector<int> result;
    int surface = 0;
    int surfaceRect = lng * wdth;
    int c1;
    while(surface < surfaceRect)
    {
        if(lng > wdth){
            c1 = wdth;
            lng -= wdth;
        }else{
            c1 = lng;
            wdth -= lng;
        }
        surface += (c1*c1);
        result.push_back(c1);

    }
    return result;
}
void printElem(int e)
{
    cout << " "<<e;
}
int main() {
    Kata k;
    vector<int> numeros;
    numeros.emplace_back(2);
    numeros.emplace_back(4);
    numeros.emplace_back(3);
    numeros.emplace_back(1);
    numeros.emplace_back(7);
    numeros.emplace_back(9);
    vector<int> squared(numeros.size());
    vector<int> squaredEmpty;
    std::sort(numeros.begin(), numeros.end());
    std::transform(numeros.begin(), numeros.end(), squared.begin(),[](int e){return (e*e);});
    // error: 'squaredEmpty' is not captured if I don't put &squaredEmpty in []
    std::for_each(numeros.begin(), numeros.end(),[&squaredEmpty](int e){squaredEmpty.emplace_back(e*e);});
    std::for_each(numeros.begin(), numeros.end(),[](int v) {cout <<" "<< v;});
    cout << endl;
    std::for_each(squared.begin(), squared.end(),printElem);
    cout << endl;
    std::for_each(squaredEmpty.begin(), squaredEmpty.end(),printElem);
    cout << endl << Kata::classVar;
        return 0;
    }
