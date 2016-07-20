#include <iostream>
#include <fstream>
#include <string>
using namespace std;
int main(int argc, char** argv) {

  ifstream input(argv[1]);
  if(!input) {
    cout<<("Error open input!\n");
    return 1;
  }
  ofstream output("result");
  if(!output) {
    printf("Error open output!\n");
    return 1;
  }
  string line;
    long long count = 0;
  while(getline(input, line)) {
    if(!line.empty()){
      size_t score_b = line.find("\"score\":") + 8;
      size_t scorelen = (score_b == string::npos)? 0 : line.find(",", score_b) - score_b;
      size_t body_b = line.find("\"body\":\"") + 8;
      size_t bodylen = (body_b == string::npos)? 0 : line.find("\",\"", body_b) - body_b;
      size_t downs_b = line.find("\"downs\":") + 8;
      size_t downslen = (downs_b == string::npos)? 0 : line.find(",", downs_b) - downs_b;
      size_t ups_b = line.find("\"ups\":") + 6;
      size_t upslen = (ups_b == string::npos)? 0 : line.find(",", ups_b) - ups_b;
      string temp = line.substr(body_b, bodylen) + "|" + line.substr(score_b, scorelen) + "|" + line.substr(ups_b, upslen) + "|" + line.substr(downs_b, downslen) + "\n";
      output<<temp;
      line.clear();
      cout<<count++;
    }
  }
  input.close();
  output.close();
  return 0;
}
