package clashcode.logic

import scala.util.parsing.combinator._

class Token(str: String) {
  val length = str.length()
}

case class Word(str: String) extends Token(str)
case class NonWord(str: String) extends Token(str)

object WordTokenizer extends RegexParsers {
  override val skipWhitespace = false
  
  def word: Parser[Word] = "[a-zA-Z]+".r ^^ { Word(_) }
  def nonWord: Parser[NonWord] = "[^a-zA-Z]+".r ^^ { NonWord(_) }
  def tokensParser = (nonWord | word)*

  def tokenize(input: String): List[Token] = {
    parseAll(tokensParser, input) match {
      case Success(tokens, _) => tokens
      case NoSuccess(_, _) => List()
    }
  }

}
