package main;


	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.ArrayList;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;


	public class SubtitlesMain {
	Pattern p;
	Matcher m;

	    		  

		public void checkRegex(String s, int wierszW)  {
			  
			// sprawdza format {798}{987}kjdhs..
			 p = Pattern.compile("^\\{\\d+\\}\\{\\d+\\}.*");
			 m = p.matcher(s);
			if(!m.matches())
				throw new NumberFormatException("NIEPOPRAWNY format wiersz: "+wierszW );
		}
			
		private void delay(String in, String out, int delay, int fps) throws IOException {
			BufferedReader input;
			String s;
			StringBuilder sb = new StringBuilder();
			int lineCount=1;
			
				
				
					input = new BufferedReader(new FileReader(in));
					while((s=input.readLine())!=null){
						checkRegex(s, lineCount++); // Sprawdzi poprawny format w danym wierszu
						sb.append(s+ "\n");
					}
				System.out.println("Format poprawny napisów");
					input.close();
					// ciag znaków przekazny do zmiennej txt z stringbuildera
					String txt = sb.toString();
					// WSZYSTKO CO ZAPISA£ StringBuilder 
					// Trzeba wy³apaæ regexem liczby z klamer  np {12324}{12324}
					
//	     I. KONTENER Z KLATKAMI -------------------------------
					// kasuje sb, czyli string buildera aby móc ponownie zapisywaæ w nim ci¹gi znaków
					sb.delete(0,sb.length());
					// Tworzê kontener na klatki
					ArrayList<Integer> list = new ArrayList<Integer>();
					// Tworzê tablicê z wierszami 
					String[] nowyTxt = txt.split("\n");
					// Teraz wyra¿eniem wy³apiê liczby z klamrami
					p = Pattern.compile("^\\{\\d+\\}\\{\\d+}");
					for(String s1:  nowyTxt){
						m = p.matcher(s1);
						while(m.find()){
							/*to tylko s³u¿y³o do sprawdzenia poprawnosci
							System.out.println(m.group()); */
				    // Zape³niam stringbuildera tylko liczbami i obrabiam wstêpnie z tych klamer
							sb.append(m.group().replaceAll("}", ""));
						}
					}
					// Tworzê tablicê lecz ju¿ samych liczb (bez klamer)
					// Pozby³em siê klamer split()
			         String[]  nowyTxt2 =sb.toString().split("\\{");
			         for(int i = 1;i <nowyTxt2.length;i++)
	   //   II. Dodano same klatki do kontenera obliczone opó¿nienie !!!
			        	  list.add(Integer.parseInt(nowyTxt2[i]) +fps* delay);
			         /*to tylko s³u¿y³o do sprawdzenia poprawnosci
			        	  System.out.println(list); */
			         
//			III. KONTENER Z NAPISAMI -------------------------------------------------	
			       
			        	  // Tworzê kontener na same napisy
			        	  ArrayList<String> list2 = new ArrayList<String>();
			        	  // Tworzê tablicê z wierszami
			        	  String [] nowyNapisy = txt.toString().split("\n");
			        	  // Zerujê stringbuildera
			        		sb.delete(0, sb.length());
			        	  // Dla wszystkich elementów tablicy zastêpuje liczby 
			        	  // z klamrami - pustym znakiem. Teraz bêdê mia³ tylko napisy
			        	  // zapisanem ci¹giem w stringbuilderze
			        	  for(String s4 : nowyNapisy)
			        	    sb.append(s4.replaceAll("^\\{\\d+\\}\\{\\d+}", " ") + "\n");
			        	  
			        	  txt= sb.toString();
			        	  //  Tworzê tablicê samych napisów
			        	  nowyNapisy =txt.split("\n"); 
			              // Pakowanie do Kontenera samych napisów//
			        	  for(int i = 0;i< nowyNapisy.length ;i++)
			        		  list2.add(nowyNapisy[i]);
			        	  /*to tylko s³u¿y³o do sprawdzenia poprawnosci 
			        		System.out.println(list2); */
			        		
//		   IV. ZAPIS NOWEGO PLIKU Z NOWYMI KLATKAMI I STARE NAPISY -----------------------------------
			        		//zeruje StringBuildera poraz kolejny
			        		sb.delete(0, sb.length());
			        		int zz=0;
			        		// UWAGA ! teraz zapisujê w formacie:
			        		// {nowaKlatka}{nowaKlatka} tekst napisów...
			        			for(int i = 0;i < list.size();i++){
			        				
			        					sb.append("{"+list.get(i)+"}");
			        					sb.append("{"+list.get(++i)+"}");
			        					sb.append(list2.get(zz++)+ "\n");
			        				
			        			}
			        				System.out.println(sb.toString());
			        			PrintWriter output = new PrintWriter(out);
			        		// Tworzê tablicê z klatkami w klamrach i sklejone napisy	
			        			String[] subtitles = sb.toString().split("\n");
			        		// Zapisujê wiersz po wierszu w nowym pliku 	
			        			for(String s6 : subtitles)
			        			if(s6!=null)
			        				output.println(s6);
			        				output.close();
		}
		
		public static void main(String[] args)  {
			
			String in= "gravity.txt";
			String out= "gravityNew.txt"; 
			SubtitlesMain nf = new SubtitlesMain();
			
			//Test zapisu
			try {
				nf.delay(in, out, 25, 1); // fps = 25 kl/s i opóŸnienie 1 sekunda
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.print("Nie znaleziono pliku:  ");
			e.printStackTrace();
		}
	      
//			// Test sprawdzania klamer //
////			nf.checkRegex("{175}{200}Na  | sjfdkh946gsdjhgdss");
	//
//			
//			
//			// in - plik wejsciowy
//			// out - plik wyjœciowy
//			// delay - opóŸnienie w milisekundach 
//			//framerate (fps) = ilosc klatek/sekunde
    	
	        }
		}




