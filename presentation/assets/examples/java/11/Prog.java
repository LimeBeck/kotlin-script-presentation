///usr/bin/env java "$0" "$@" ; exit $?

class Prog {
    public static void main(String[] args) { Helper.run(); }
}

class Helper {
    static void run() { System.out.println("Hello!"); }
}