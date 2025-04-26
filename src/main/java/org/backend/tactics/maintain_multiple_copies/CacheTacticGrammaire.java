
package org.backend.tactics.maintain_multiple_copies;
import org.backend.tactics.maintain_multiple_copies.CacheTacticGrammaireConstants;
import org.backend.tactics.maintain_multiple_copies.ParseException;
import org.backend.tactics.maintain_multiple_copies.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
public class CacheTacticGrammaire implements CacheTacticGrammaireConstants {
    public static int i=1;
    public static Map<String, String> map = new HashMap<>();
    public static Map<String, String> tacticParticipatingObjects = new HashMap<>();
    public static int numberOfCacheInteractions = 0;
    public  static StringBuilder parseSteps = new StringBuilder();
    public   static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws ParseException {
        try {
            CacheTacticGrammaire parser = new CacheTacticGrammaire(new java.io.FileInputStream("C:/Users/admin/eclipse-workspace/MaintainMultipleCopiesTactic2/src/MaintainMultipleCopiesTactic2.txt"));// according to our trace

            try {
                parser.CachePattern();

                if (!tacticParticipatingObjects.isEmpty()) {
                    System.out.println("\n===================RESULT===================\n");
                    System.out.println("Tactic Participating Objects:\n");
                    for (String key : tacticParticipatingObjects.keySet()) {
                        String value = tacticParticipatingObjects.get(key);
                        System.out.println(key + " --> " + value);
                    }
                    System.out.println("Number of Cache Interactions in the trace:\n " + numberOfCacheInteractions);
                     System.out.println("\n============================================\n");
                }

            } catch (Exception e) {
                System.out.println("Error in Parsing:\n");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("File Not Found or Error Reading File.\n");
        }
    }
    public  String parseAndGetResult(InputStream inputStream) {
        map.clear();
        tacticParticipatingObjects.clear();
        numberOfCacheInteractions = 0;
        i=1;
        result.setLength(0);
        parseSteps.setLength(0);
        // Clear any old steps

        try {
            CacheTacticGrammaire parser = new CacheTacticGrammaire(inputStream);

            try {
                parser.CachePattern();

                if (!tacticParticipatingObjects.isEmpty()) {
                    result.append("\n==================RESULT===================\n");
                    result.append("Tactic Participating Objects:\n");
                    for (Map.Entry<String, String> entry : tacticParticipatingObjects.entrySet()) {
                        result.append(entry.getKey()).append(" --> ").append(entry.getValue()).append("\n");
                    }
                    result.append("Number of Cache Interactions in this trace: ").append(numberOfCacheInteractions).append("\n");
                    result.append("============================================\n");
                } else {
                    result.append("\nNo Cache Tactic detected.\n");
                }

            } catch (Exception e) {
                result.append("Parsing Error: ").append(e.getMessage()).append("\n");
            }

        } catch (Exception e) {
            result.append("File Error: ").append(e.getMessage()).append("\n");
        }

        // Add the steps from inside the grammar rules
        result.append(parseSteps.toString());

        return result.toString();
    }

  final public void CachePattern() throws ParseException {
    Request();
    lookup();
    recursivecall();
}

// the call of  request(key)()
  final public void Request() throws ParseException {Token caller, callee;
    jj_consume_token(CALLER);
    caller = jj_consume_token(LITERAL);
map.put("caller", caller.image);
    jj_consume_token(COMMA);
    S();
    callee = jj_consume_token(LITERAL);
map.put("callee1", callee.image);
    jj_consume_token(COMMAP);
}

// the call of  lookup(key)()
  final public void lookup() throws ParseException {Token caller, callee;
    if (getToken(2).image.equals(map.get("callee1"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      S();
      callee = jj_consume_token(LITERAL);
      map.put("callee2", callee.image);
    if (caller.image.equals(map.get("caller")) || callee.image.equals(map.get("callee1")) || callee.image.equals(map.get("caller"))) {{if (true) throw new ParseException(" Not expected callee");}}
     else
     {
       tacticParticipatingObjects.put("CacheClient", map.get("caller"));
       tacticParticipatingObjects.put("CacheMgr", caller.image);
       tacticParticipatingObjects.put("Cache", callee.image);
       OPTlookup(); //enter the opt block(call of first method) 
      }
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CALLER:{
    	i++;
        CachePattern();
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void OPTlookup() throws ParseException {Token caller, callee;
  jj_consume_token(COMMAP);
    if (getToken(2).image.equals(map.get("callee1"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      S();
      callee = jj_consume_token(LITERAL);
tacticParticipatingObjects.put("Repository", callee.image);
        map.put("callee3", callee.image);
     if (callee.image.equals(map.get("callee1")) || callee.image.equals(map.get("callee2")) || callee.image.equals(map.get("caller"))) {{if (true) throw new ParseException(" Not expected callee");}}
     else {
       OPTupdate(); //(call of second method) 
     }
    } else {
numberOfCacheInteractions++;
        result.append("==>The Maintain Multiple Copies (Cache) tactic is detected at line "+i);
        result.append("\n");
        result.append(map.get("caller") + "--Request-->" + map.get("callee1"));
        result.append("\n");
        result.append(map.get("callee1") + "--lookup-->" + map.get("callee2"));
        result.append("\n");
    i++;

    }
}

  final public void OPTupdate() throws ParseException {Token caller, callee;
  jj_consume_token(COMMAP);
    if (getToken(2).image.equals(map.get("callee1"))) {
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      S();
      callee = jj_consume_token(LITERAL);
if(!(callee.image.equals(map.get("callee2"))))  {{if (true) throw new ParseException(" Not expected callee");}}
       else {
         numberOfCacheInteractions++;
         result.append("==>The Maintain Multiple Copies (Cache) tactic is detected at line "+i);
         result.append("\n");
         result.append(map.get("caller") + "--Request-->" + map.get("callee1"));
         result.append("\n");
         result.append(map.get("callee1") + "--lookup-->" + map.get("callee2"));
         result.append("\n");
         result.append(map.get("callee1") + "--lookup-->" + map.get("callee3"));
         result.append("\n");
         result.append(map.get("callee1") + "--update-->" + map.get("callee2"));
         result.append("\n");
         i=i+3;
       }
      jj_consume_token(COMMAP);
    } else {
      i=i+3;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CALLER:{
        CachePattern();
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  final public void recursivecall() throws ParseException {Token caller, callee;
    i++;
    if (getToken(2).image.equals(map.get("caller"))) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CALLER:{
        Request();
        lookup();
        recursivecall();
        break;
        }
      default:
        jj_la1[2] = jj_gen;
      }
    } else {
      callsegment();
    }
}

  final public void callsegment() throws ParseException {Token caller, callee;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CALLER:{
      jj_consume_token(CALLER);
      caller = jj_consume_token(LITERAL);
      jj_consume_token(COMMA);
      S();
      callee = jj_consume_token(LITERAL);
      jj_consume_token(COMMAP);
      recursivecall();
      break;
      }
    default:
      jj_la1[3] = jj_gen;
    }
}

  final public void S() throws ParseException {
    jj_consume_token(METHOD);
    jj_consume_token(LITERAL);
    jj_consume_token(COMMA);
    jj_consume_token(CALLEE);
}

  final public void commap() throws ParseException {
    jj_consume_token(COMMA);
}

  /** Generated Token Manager. */
  public CacheTacticGrammaireTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
	   jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x80,0x80,0x80,0x80,};
	}

  /** Constructor with InputStream. */
  public CacheTacticGrammaire(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public CacheTacticGrammaire(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new CacheTacticGrammaireTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
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
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public CacheTacticGrammaire(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new CacheTacticGrammaireTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new CacheTacticGrammaireTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public CacheTacticGrammaire(CacheTacticGrammaireTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(CacheTacticGrammaireTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
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

  private final java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[12];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 4; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 12; i++) {
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
