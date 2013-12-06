package clashcode.wordguess.logic

import scala.io.Source
import org.apache.commons.lang.StringEscapeUtils
import java.io.Writer
import java.io.File
import java.io.FileWriter
import play.api.Play
import play.api.Play.current
import clashcode.wordguess.persistence.DAO

trait GameStatePersistence {

  val separatorChar = '§'
  
  def load(): GameState = {
    val wordStates = (for {
      line <- DAO.getGameState
      if (!line.trim().isEmpty())
    } yield {
      val parts = line.split(separatorChar)
      if (parts.length == 2) {
        val solved = parts(0) == "S"
        // Scala src does one escaping for us
        val word = StringEscapeUtils.unescapeJava(
          StringEscapeUtils.unescapeJava(parts(1)))
        WordState(word, solved)
      } else {
        println("Problem with line: " + StringEscapeUtils.escapeJava(line))
        WordState("?", solved=true)
      }
    }).toList
    GameState(wordStates)
  }

//  val shit =
//    """
//      |Ambohimanga is a hill and traditional fortified royal settlement (rova) in Madagascar, located approximately 24 kilometres (15 mi) northeast of the capital city of Antananarivo. The hill and the rova that stands on top are considered the most significant symbol of the cultural identity of the Merina people and the most important and best-preserved monument of the precolonial Kingdom of Madagascar and its precursor, the Kingdom of Imerina. The walled historic village includes residences and burial sites of several key monarchs. The site, one of the twelve sacred hills of Imerina, is associated with strong feelings of national identity and has maintained its spiritual and sacred character both in ritual practice and the popular imagination for at least four hundred years. It remains a place of worship to which pilgrims come from Madagascar and elsewhere.
//      |The site has been politically important since the early 18th century, when King Andriamasinavalona (1675–1710) divided the Kingdom of Imerina into four quadrants and assigned his son Andriantsimitoviaminiandriana to govern the northeastern quadrant, Avaradrano, from its newly designated capital at Ambohimanga. The division of Imerina led to 77 years of civil war, during which time the successive rulers of Avaradrano led military campaigns to expand their territory while undertaking modifications to the defenses at Ambohimanga to better protect it against attacks. The war was ended from Ambohimanga by King Andrianampoinimerina, who successfully undertook negotiations and military campaigns that reunited Imerina under his rule by 1793. Upon capturing the historic capital of Imerina at Antananarivo, Andrianampoinimerina shifted his royal court and all political functions back to its original locus at that city's royal compound and declared the two cities of equal importance, with Ambohimanga as the kingdom's spiritual capital. He and later rulers in his line continued to conduct royal rituals at the site and regularly inhabited and remodeled Ambohimanga until French colonization of the kingdom and the exile of the royal family in 1897. The significance of historical events here and the presence of royal tombs have given the hill a sacred character that is further enhanced at Ambohimanga by the burial sites of several Vazimba, the island's earliest inhabitants.
//      |The royal compound on the hilltop is surrounded by a complex system of defensive ditches and stone walls and is accessed by 14 gateways, of which many were sealed by stone disc barriers. The gateways and construction of buildings within the compound are arranged according to two overlaid cosmological systems that value the four cardinal points radiating from a unifying center, and attach sacred importance to the northeastern direction. The complex inside the wall is subdivided into three smaller rova. Mahandrihono, the largest compound, was established between 1710 and 1730 by King Andriambelomasina; it remains largely intact and contains the royal tombs, house of King Andrianampoinimerina, summer palace of Queen Ranavalona II, and sites that figured in key royal rituals such as the sacrificial zebu pen, royal bath and main courtyard. Original buildings no longer remain in the compound of Bevato, established before 1710 by Andriamborona, and the Nanjakana compound, built for King Andrianjafy in the late 19th century. The hill and its royal fortified city were added to the list of UNESCO World Heritage Sites in 2001 and represent Madagascar's only cultural site following the destruction by fire in 1995 of its historic sister city, the Rova of Antananarivo, shortly before the latter's intended inscription to the list. Numerous governmental and civil society organizations support the conservation of Ambohimanga by restoring damaged features and preventing further degradation.
//    """.stripMargin

  def ensureGameStateFile(minGameWordLength:Int) {
    if (DAO.getGameState.isEmpty) {
      val gameState = GameStateGenerator.fromSource(minGameWordLength)
      write(gameState)
    }
  }

  def write(state: GameState) {
    DAO.deleteAllGameState
    for (wordState <- state.wordStates) {
      val stateLetter = if (wordState.solved) "S" else "U"
      val escapedWord = StringEscapeUtils.escapeJava(wordState.word)
      DAO.insertGameState(s"$stateLetter$separatorChar$escapedWord")
    }
  }

}