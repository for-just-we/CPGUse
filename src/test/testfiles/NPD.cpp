
struct S1 {
	char c1; //1个字节
	int i;   //4个字节
	char c2; // 1个字节
};

int main() {
    struct S1 *s = nullptr; // 可以识别nullptr关键字,NULL不行
    // s = new S1();
    s->i = 1;
    return 0;
}