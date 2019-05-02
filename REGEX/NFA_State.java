package com.company.REGEX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class NFA_State {
      int id;
      TreeMap<Character, ArrayList<NFA_State>> map;
      boolean AcceptingState;
      TreeSet<Character> input;
      HashSet<NFA_State> states;
      NFA_State(int id)
      {
            this.id=id;
            AcceptingState=false;
            map=new TreeMap<>();
            input=new TreeSet<>();
            states=new HashSet<>();
      }
      NFA_State(int id,HashSet<NFA_State> set)
      {
            this.id=id;
            AcceptingState=false;
            map=new TreeMap<>();
            input=new TreeSet();
            states=set;

            for(NFA_State ss:set)
            {
                  if(ss.AcceptingState){
                        this.AcceptingState=true;
                        break;
                  }
            }
      }

      void setTransition(char c,NFA_State s)
      {
            ArrayList<NFA_State> temp=new ArrayList<>();
            map.putIfAbsent(c,temp);
            input.add(c);
            map.get(c).add(s);
      }

      ArrayList<NFA_State> getTransition(char c)
      {
            return map.get(c);
      }
      boolean TransitionPossible(char c){
            return input.contains(c);
      }



}
