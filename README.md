# peko
Peko is a simple imperative programming lnauge utilizing a custom bytecode and VM.

## Features
- C-like Syntax
- Short circuit evaluation
- Functions
- Local and global variables
- Arrays

## Usage
`java -jar peko.jar <input>`

## Example
```
func main() {
    var i = 1;
    while i <= 100 {
        if i % 3 == 0 && i % 5 == 0 {
            println("FizzBuzz");
        } else {
            if i % 3 == 0 {
                println("Fizz");
            } else {
                if i % 5 == 0 {
                    println("Buzz");
                } else {
                    println(i);
                }
            }
        }

        i = i + 1;
    }
}
```