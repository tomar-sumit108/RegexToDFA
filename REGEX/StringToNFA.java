package com.company.REGEX;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Stack;

public class StringToNFA {
      final char EPSILON=(char)0;
      Stack<ArrayDeque<NFA_State>> operandStack;
      Stack<Character> operatorStack;
      int idCount=0;
      HashSet<Character> inputSet;
      StringToNFA()
      {
            operandStack=new Stack<>();
            operatorStack=new Stack<>();
            inputSet=new HashSet<>();
            idCount=0;
      }

      boolean isChar(char a) {
            return (a!='|'&&a!='*'&&a!='('&&a!=')'&&a!=1);
      }
      boolean LeftParen(char a) {
            return (a=='(');
      }
      boolean RightParen(char b) {
            return (b==')');
      }
      String Expand(String str)
      {
            StringBuilder ss=new StringBuilder();
            for(int i=0;i<str.length()-1;i++)
            {
                  char a=str.charAt(i);
                  char b=str.charAt(i+1);
                  ss.append(a);
                  if(isChar(a))
                        inputSet.add(str.charAt(i));
                  if((isChar(a)||RightParen(a)||a=='*')&&(isChar(b)||LeftParen(b))){
                        char tt=1;
                        ss.append(tt);
                  }
            }

            ss.append(str.charAt(str.length()-1));
            if(isChar(str.charAt(str.length()-1)))
                  inputSet.add(str.charAt(str.length()-1));

            System.out.println();
            System.out.println("the StringBuilder is "+ss);
            return ss.toString();
      }
      void Push(char c)
      {
            NFA_State a=new NFA_State(idCount++);
            NFA_State b=new NFA_State(idCount++);
            a.setTransition(c,b);
            ArrayDeque<NFA_State> temp= new ArrayDeque<>();
            temp.offer(a);
            temp.offer(b);
            operandStack.push(temp);
      }

      boolean Concat()
      {
            if(operandStack.size()<2)
                  return false;
            ArrayDeque<NFA_State> d2=operandStack.pop();
            ArrayDeque<NFA_State> d1=operandStack.pop();
            d1.getLast().setTransition(EPSILON,d2.getFirst());
            d1.addAll(d2);
            operandStack.push(d1);
            return true;
      }
      boolean Star()
      {
            if(operandStack.empty())
                  return false;
            ArrayDeque<NFA_State> d1=operandStack.pop();
            NFA_State a=new NFA_State(idCount++);
            NFA_State b=new NFA_State(idCount++);
            a.setTransition(EPSILON,b);
            a.setTransition(EPSILON,d1.getFirst());
            d1.getLast().setTransition(EPSILON,b);;
            d1.getLast().setTransition(EPSILON,d1.getFirst());
            d1.offerLast(b);
            d1.offerFirst(a);
            operandStack.push(d1);
            return true;
      }
      boolean Union()
      {

            if(operandStack.size()<2)
                  return false;
            ArrayDeque<NFA_State> d2=operandStack.pop();
            ArrayDeque<NFA_State> d1=operandStack.pop();
            NFA_State a=new NFA_State(idCount++);
            NFA_State b=new NFA_State(idCount++);
            a.setTransition(EPSILON,d1.getFirst());
            a.setTransition(EPSILON,d2.getFirst());
            d1.getLast().setTransition(EPSILON,b);
            d2.getLast().setTransition(EPSILON,b);
            d1.offerFirst(a);
            d1.addAll(d2);
            d1.offer(b);
            operandStack.push(d1);
            return true;
      }

      boolean Evaluate()
      {
            if(operatorStack.empty())
                  return false;
            char c=operatorStack.pop();
            switch (c)
            {
                  case '*':
                        Star();
                        break;
                  case 1:
                        Concat();
                        break;
                  case '|':
                        Union();
                        break;
            }
            return true;

      }
      boolean Precedence(char a,char b)
      {
            if(a == b)
                  return true;

            if(a == '*')
                  return false;

            if(b == '*')
                  return true;

            if(a == 1)
                  return false;

            if(b == 1)
                  return true;

            if(a == '|')
                  return false;

            return true;
      }
      ArrayDeque<NFA_State> Solve(String str)
      {
            operandStack=new Stack<>();
            operatorStack=new Stack<>();
            System.out.println("The before expanding String is "+str);
            str=Expand(str);
            System.out.println("The expanded String is "+str);
            for(int i=0;i<str.length();i++)
            {
                  char cc=str.charAt(i);
                  if(isChar(cc))
                        Push(cc);
                  else if(operatorStack.empty())
                        operatorStack.push(cc);
                  else if(LeftParen(cc))
                        operatorStack.push(cc);
                  else if(RightParen(cc))
                  {
                        while(LeftParen(operatorStack.peek()))
                        {
                              Evaluate();
                        }
                        operatorStack.pop();
                  }
                  else
                  {
                        while(!operatorStack.empty()&&Precedence(cc,operatorStack.peek()))
                        {
                              Evaluate();
                        }
                        operatorStack.push(cc);

                  }
            }

            while(!operatorStack.empty())
                  Evaluate();

            ArrayDeque<NFA_State> ans;
            ans=operandStack.pop();
            ans.getLast().AcceptingState=true;
            return ans;
      }

}
