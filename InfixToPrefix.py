from collections import defaultdict

def infixToPrefix(expression):
    precedence = defaultdict(int)
    precedence["+"] = 1
    precedence["-"] = 1
    precedence["*"] = 2
    precedence["/"] = 2
    precedence["^"] = 3

    operatorStack = []
    operandStack = []

    expression = expression.split()
    for currentCharacter in expression:
        if currentCharacter.isalnum():
            operandStack.append(currentCharacter)
        elif currentCharacter == "(":
            operatorStack.append(currentCharacter)
        elif currentCharacter == ")":
            while operatorStack[-1] != "(":
                operand2 = operandStack.pop()
                operand1 = operandStack.pop()
                operator = operatorStack.pop()
                operandStack.append(operator + " " + operand1 + " " + operand2)
            operatorStack.pop()
        else:
            while operatorStack and precedence[currentCharacter] <= precedence[operatorStack[-1]]:
                operand2 = operandStack.pop()
                operand1 = operandStack.pop()
                operator = operatorStack.pop()
                operandStack.append(operator + " " + operand1 + " " + operand2)
            operatorStack.append(currentCharacter)

    while operatorStack:
        operand2 = operandStack.pop()
        operand1 = operandStack.pop()
        operator = operatorStack.pop()
        operandStack.append(operator + " " + operand1 + " " + operand2)

    return operandStack[0]

expression = input("Infix expression: ")
print("Prefix expression: ", infixToPrefix(expression))
