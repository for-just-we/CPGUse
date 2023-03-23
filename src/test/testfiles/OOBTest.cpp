#include <iostream>
using namespace std;

int main() {
    int *a = new int[9 + 1];
    int i = 10;
    int idx = i + 1;
    a[idx] = 13;
    return 0;
}