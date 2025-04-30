
package org.backend.tactics.onetime_password;
import org.backend.tactics.onetime_password.OneTimePasswordTacticGrammarConstants;
import org.backend.tactics.onetime_password.OneTimePasswordTacticGrammarTokenManager;
import  org.backend.tactics.onetime_password.ParseException;
import  org.backend.tactics.onetime_password.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.Objects;
import java.io.InputStream;

public class OneTimePasswordTacticGrammar implements OneTimePasswordTacticGrammarConstants {
  public static Map<String, String> CallerCalleeMap = new HashMap<>();
  public static Map<String, String> TacticEntities = new HashMap<>();
  public static int CheckCount = 0;
  public static int Lines = 1;
    public  static StringBuilder parseSteps = new StringBuilder();
    public   static StringBuilder result = new StringBuilder();

    public static void main(String args []) throws ParseException
  {
    try {
            OneTimePasswordTacticGrammar parser = new OneTimePasswordTacticGrammar(
                new java.io.FileInputStream("C:/Users/hp/eclipse-workspace/OneTimePassword/src/OneTimePasswordExecutionTrace.txt")); //Add your execution trace path here 
            try {
                             parser.OneTimePassword();
                              if(!TacticEntities.isEmpty()) {
                                 System.out.println("\n===================RESULT===================\n");
                                 System.out.println("TacticEntities are: ");
                                 for(String key : TacticEntities.keySet()) {
                                    String value = TacticEntities.get(key);
                                    System.out.println(key + " --> " + value);
                                 }
                                 System.out.println("The One Time password tactic is detected "+CheckCount+" times in your execution trace");
                     System.out.println("\n============================================\n");

                              }
                } catch (Exception e) {
                              System.out.println("Oops.");
                              System.out.println(e.getMessage());
                              e.printStackTrace();
                }
    } catch(Exception e) {
        e.printStackTrace();
    }
  }
    public  String parseAndGetResult(InputStream inputStream) {
        CallerCalleeMap.clear();
        TacticEntities.clear();
        CheckCount = 0;
        Lines=1;
        result.setLength(0);
        parseSteps.setLength(0);
        // Clear any old steps
        try {
        OneTimePasswordTacticGrammar parser = new OneTimePasswordTacticGrammar(inputStream);

            try {
                parser.OneTimePassword();
                if(!TacticEntities.isEmpty()) {
                    result.append("\n====================RESULT====================\n");
                    result.append("tactic participating objects\n");
                    for(String key : TacticEntities.keySet()) {
                        String value = TacticEntities.get(key);
                        result.append(key + " --> " + value + "\n");
                    }
                    result.append("The One Time password tactic is detected "+CheckCount+" times in your execution trace\n");
                    result.append("\n===============================================\n");

                } else {
                    result.append("\n");
                    result.append("No OneTime Password Tactic detected.");
                    result.append("\n");
                }

            } catch (Exception e) {
                result.append("Parsing Error: ").append(e.getMessage()).append("\n");
            }
        } catch (Exception e) {
            result.append("File Error: ").append(e.getMessage()).append("\n");
        }


            result.append(parseSteps.toString());

        return result.toString();
    }


    final public void OneTimePassword() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CALLER:{
      RequestAuthentication();
      GenSecureVal();
      sendSecureVal();
      GenCPasswrd();
      GenPasswrd();
      SendPasswrd();
      CheckVerify();
      RecursiveLoop();
      break;
      }
    case 0:{
      jj_consume_token(0);
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void RequestAuthentication() throws ParseException {Token caller, callee;
    jj_consume_token(CALLER);
    caller = jj_consume_token(LITERAL);
CallerCalleeMap.put("caller", caller.image);
    jj_consume_token(COMMA);
    Skiping();
    callee = jj_consume_token(LITERAL);
CallerCalleeMap.put("callee", callee.image);
    jj_consume_token(SEMICOLON);
}

  final public void GenSecureVal() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("callee"))) {
      if (!Objects.equals(CallerCalleeMap.get("caller"), CallerCalleeMap.get("callee"))) {
        jj_consume_token(CALLER);
        caller = jj_consume_token(LITERAL);
        jj_consume_token(COMMA);
        Skiping();
        callee = jj_consume_token(LITERAL);
        jj_consume_token(SEMICOLON);
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 0:
        case CALLER:{
Lines++ ;
          OneTimePassword();
          break;
          }
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 0:
      case CALLER:{
Lines++ ;
        OneTimePassword();
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void sendSecureVal() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("callee"))  && getToken(8).image.equals(CallerCalleeMap.get("caller"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      Skiping();
      callee = jj_consume_token(LITERAL);
      jj_consume_token(SEMICOLON);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 0:
      case CALLER:{
Lines=Lines+2 ;
        OneTimePassword();
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void GenCPasswrd() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("callee"))  && getToken(8).image.equals(CallerCalleeMap.get("callee"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      Skiping();
      callee = jj_consume_token(LITERAL);
      jj_consume_token(SEMICOLON);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 0:
      case CALLER:{
Lines=Lines+3 ;
        OneTimePassword();
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void GenPasswrd() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("caller"))  && getToken(8).image.equals(CallerCalleeMap.get("caller"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      Skiping();
      callee = jj_consume_token(LITERAL);
      jj_consume_token(SEMICOLON);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 0:
      case CALLER:{
Lines=Lines+4 ;
        OneTimePassword();
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void SendPasswrd() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("caller"))  && getToken(8).image.equals(CallerCalleeMap.get("callee"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      Skiping();
      callee = jj_consume_token(LITERAL);
      jj_consume_token(SEMICOLON);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 0:
      case CALLER:{
Lines=Lines+5 ;
        OneTimePassword();
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void CheckVerify() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("callee"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      Skiping();
      callee = jj_consume_token(LITERAL);
if (!callee.image.equals(CallerCalleeMap.get("callee"))) {
                    {if (true) throw new ParseException("In CheckVerify Method: Not expected callee");}
                   } else {
                    TacticEntities.put("Client",CallerCalleeMap.get("caller"));
                    TacticEntities.put("Authenticator", caller.image);
                    CheckCount++;
    result.append("\n==>The One Time Password tactic is detected here at line "+Lines);
    result.append("\n");
    result.append(CallerCalleeMap.get("caller") + " --RequestAuthen(id)--> " + callee.image);
    result.append("\n");
    result.append(callee.image + " --GenSecureValue()--> " + callee.image);
    result.append("\n");
    result.append(callee.image + " --sendSecureValue(lsv)--> " + CallerCalleeMap.get("caller"));
    result.append("\n");
    result.append(callee.image + " --GenCPasswrd(lsv)--> " + callee.image);
    result.append("\n");
    result.append(CallerCalleeMap.get("caller") + " --GenPasswrd(lsv)--> " + CallerCalleeMap.get("caller"));
    result.append("\n");
    result.append(CallerCalleeMap.get("caller") + " --SendPassword(lpw)--> " + callee.image);
    result.append("\n");
    result.append( callee.image+ " --CheckVerify(lcpw,lpw)--> " + callee.image);
    result.append("\n");
                        { Lines=Lines+6 ; }
                        }
      jj_consume_token(SEMICOLON);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 0:
      case CALLER:{
Lines=Lines+6 ;
        OneTimePassword();
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void RecursiveLoop() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(CallerCalleeMap.get("caller"))) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CALLER:{
Lines++ ;
        RequestAuthentication();
        GenSecureVal();
        sendSecureVal();
        GenCPasswrd();
        GenPasswrd();
        SendPasswrd();
        CheckVerify();
        RecursiveLoop();
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        ;
      }
    } else {
Lines++ ;
      NextLine();
    }
}

  final public void NextLine() throws ParseException {Token caller, callee;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CALLER:{
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      Skiping();
      callee = jj_consume_token(LITERAL);
      jj_consume_token(SEMICOLON);
      RecursiveLoop();
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      ;
    }
}

  final public void Skiping() throws ParseException {
    jj_consume_token(METHOD);
    jj_consume_token(LITERAL);
    jj_consume_token(COMMA);
    jj_consume_token(CALLEE);
}

  /** Generated Token Manager. */
  public OneTimePasswordTacticGrammarTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[10];
  static private int[] jj_la1_0;
  static {
	   jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x401,0x401,0x401,0x401,0x401,0x401,0x401,0x401,0x400,0x400,};
	}

  /** Constructor with InputStream. */
  public OneTimePasswordTacticGrammar(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public OneTimePasswordTacticGrammar(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new OneTimePasswordTacticGrammarTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public OneTimePasswordTacticGrammar(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new OneTimePasswordTacticGrammarTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new OneTimePasswordTacticGrammarTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public OneTimePasswordTacticGrammar(OneTimePasswordTacticGrammarTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(OneTimePasswordTacticGrammarTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[15];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 10; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 15; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
