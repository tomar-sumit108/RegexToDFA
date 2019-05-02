package com.company.REGEX;


import java.lang.reflect.Array;
import java.util.*;

public class NfaToDfa {
      final char EPSILON=(char)0;
      ArrayDeque<NFA_State> nfa;
      ArrayDeque<NFA_State> dfa;
      int nextId=0;
      HashSet<Character> inputSet;

      NfaToDfa(ArrayDeque<NFA_State> nfa,HashSet<Character> inputSet)
      {
            this.nfa=nfa;
            this.inputSet=inputSet;
            dfa=new ArrayDeque<>();
      }

      HashSet<NFA_State> EpsilonClosure(HashSet<NFA_State> set)
      {
            Stack<NFA_State> upStack=new Stack<>();
            HashSet<NFA_State> res=new HashSet<>();
            res.addAll(set);
            upStack.addAll(set);

            while(!upStack.empty())
            {
                   NFA_State tempState=upStack.pop();

                  ArrayList<NFA_State> list=tempState.getTransition((char)0);
                  if(list!=null)
                  for(NFA_State tt:list) {
                        if (!res.contains(tt))
                        {
                             res.add(tt);
                             upStack.push(tt);
                        }
                  }

            }
            return res;
      }

      HashSet<NFA_State> Move(char c,HashSet<NFA_State> set){
            HashSet<NFA_State> res=new HashSet<>();
            for(NFA_State temp:set)
            {
                  ArrayList<NFA_State> list=temp.getTransition(c);
                  if(list!=null)
                  res.addAll(list);
            }
            return res;
      }

      ArrayDeque<NFA_State> convert()
      {

            Stack<NFA_State> unMarked=new Stack<>();
            HashSet<NFA_State> dfaStartSet=new HashSet<>();
            HashSet<NFA_State> nfaStartSet=new HashSet<>();
            nfaStartSet.add(nfa.getFirst());
            dfaStartSet=EpsilonClosure(nfaStartSet);

            NFA_State newState=new NFA_State(nextId++,dfaStartSet);
            dfa.push(newState);
            unMarked.push(newState);
            while(!unMarked.empty())
            {
                  NFA_State toProcess=unMarked.pop();
                  for(Character input:inputSet)
                  {
                        HashSet<NFA_State> result=Move(input,toProcess.states);
                        result=EpsilonClosure(result);
                        boolean found=false;
                        NFA_State toAdd=null;
                        for(NFA_State tt:dfa)
                        {
                              if(result.equals(tt.states))
                              {
                                    found=true;
                                    toAdd=tt;
                                    break;
                              }
                        }
                        if(!found)
                        {
                              NFA_State nnst=new NFA_State(nextId++,result);                              unMarked.push(nnst);
                              dfa.offer(nnst);
                              toProcess.setTransition(input,nnst);
                        }
                        else {
                            toProcess.setTransition(input,toAdd);
                        }
                  }
            }
            return dfa;
      }
}
