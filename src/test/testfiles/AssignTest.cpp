
void swap(int *p, int *q) {
    int t = *p;
    *p = *q;
    *q = t;
}

int main() {
    int a = 2, b = 1;
    swap(&a, &b);
    int c = a + 1;
    return 0;
}