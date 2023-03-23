#include<stdio.h>

int func(int a, int b) {
    int c = a + b, d;
    if (c == 1)
        d = 0;
    else
        d = 1;
    return d;
}

int main() {
   int a, b;
   scanf("%d, %d", &a, &b);
   int d = func(a, b);

   while (true);
   return 0;
}