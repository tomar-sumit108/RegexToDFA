package com.company.REGEX;

import java.util.ArrayDeque;
import java.util.Stack;

public class Regex {

      ArrayDeque<NFA_State> NFA_Table;
      ArrayDeque<NFA_State> DFA_Table;
      public Regex(){
            NFA_Table=new ArrayDeque<>();
      }
      public boolean solve(String str)
      {
            StringToNFA converter=new StringToNFA();
            NFA_Table=converter.Solve(str);
            for(NFA_State temp:NFA_Table)
            {
                  System.out.println("For the state "+temp.id );
                  for(Character cc:temp.input)
                  {
                        System.out.print(cc+" =");
                        for(NFA_State state:temp.getTransition(cc))
                        {
                              System.out.print(state.id+" ");
                        }
                        System.out.println();
                  }

            }
            NfaToDfa converter2=new NfaToDfa(NFA_Table,converter.inputSet);
            DFA_Table=converter2.convert();

            System.out.println("/-------------------------DFA DFA--------------------/");
            for(NFA_State temp:DFA_Table)
            {
                  System.out.println("For the state "+temp.id+" "+temp.AcceptingState );
                  for(Character cc:temp.input)
                  {
                        System.out.print(cc+" =");
                        for(NFA_State state:temp.getTransition(cc))
                        {
                              System.out.print(state.id+" ");
                        }
                        System.out.println();
                  }

            }

           return true;
      }
      public boolean check(String str)
      {
            NFA_State curr=DFA_Table.getFirst();
            for(int i=0;i<str.length();i++)
            {
                  if(curr.getTransition(str.charAt(i))==null)
                  {
                        return false;
                  }
                  curr=curr.getTransition(str.charAt(i)).get(0);
            }
            return curr.AcceptingState;
      }

}
