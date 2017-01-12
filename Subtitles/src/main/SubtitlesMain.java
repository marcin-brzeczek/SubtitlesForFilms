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
				System.out.println("Format poprawny napis�w");
					input.close();
					// ciag znak�w przekazny do zmiennej txt z stringbuildera
					String txt = sb.toString();
					// WSZYSTKO CO ZAPISA� StringBuilder 
					// Trzeba wy�apa� regexem liczby z klamer  np {12324}{12324}
					
//	     I. KONTENER Z KLATKAMI -------------------------------
					// kasuje sb, czyli string buildera aby m�c ponownie zapisywa� w nim ci�gi znak�w
					sb.delete(0,sb.length());
					// Tworz� kontener na klatki
					ArrayList<Integer> list = new ArrayList<Integer>();
					// Tworz� tablic� z wierszami 
					String[] nowyTxt = txt.split("\n");
					// Teraz wyra�eniem wy�api� liczby z klamrami
					p = Pattern.compile("^\\{\\d+\\}\\{\\d+}");
					for(String s1:  nowyTxt){
						m = p.matcher(s1);
						while(m.find()){
							/*to tylko s�u�y�o do sprawdzenia poprawnosci
							System.out.println(m.group()); */
				    // Zape�niam stringbuildera tylko liczbami i obrabiam wst�pnie z tych klamer
							sb.append(m.group().replaceAll("}", ""));
						}
					}
					// Tworz� tablic� lecz ju� samych liczb (bez klamer)
					// Pozby�em si� klamer split()
			         String[]  nowyTxt2 =sb.toString().split("\\{");
			         for(int i = 1;i <nowyTxt2.length;i++)
	   //   II. Dodano same klatki do kontenera obliczone op�nienie !!!
			        	  list.add(Integer.parseInt(nowyTxt2[i]) +fps* delay);
			         /*to tylko s�u�y�o do sprawdzenia poprawnosci
			        	  System.out.println(list); */
			         
//			III. KONTENER Z NAPISAMI -------------------------------------------------	
			       
			        	  // Tworz� kontener na same napisy
			        	  ArrayList<String> list2 = new ArrayList<String>();
			        	  // Tworz� tablic� z wierszami
			        	  String [] nowyNapisy = txt.toString().split("\n");
			        	  // Zeruj� stringbuildera
			        		sb.delete(0, sb.length());
			        	  // Dla wszystkich element�w tablicy zast�puje liczby 
			        	  // z klamrami - pustym znakiem. Teraz b�d� mia� tylko napisy
			        	  // zapisanem ci�giem w stringbuilderze
			        	  for(String s4 : nowyNapisy)
			        	    sb.append(s4.replaceAll("^\\{\\d+\\}\\{\\d+}", " ") + "\n");
			        	  
			        	  txt= sb.toString();
			        	  //  Tworz� tablic� samych napis�w
			        	  nowyNapisy =txt.split("\n"); 
			              // Pakowanie do Kontenera samych napis�w//
			        	  for(int i = 0;i< nowyNapisy.length ;i++)
			        		  list2.add(nowyNapisy[i]);
			        	  /*to tylko s�u�y�o do sprawdzenia poprawnosci 
			        		System.out.println(list2); */
			        		
//		   IV. ZAPIS NOWEGO PLIKU Z NOWYMI KLATKAMI I STARE NAPISY -----------------------------------
			        		//zeruje StringBuildera poraz kolejny
			        		sb.delete(0, sb.length());
			        		int zz=0;
			        		// UWAGA ! teraz zapisuj� w formacie:
			        		// {nowaKlatka}{nowaKlatka} tekst napis�w...
			        			for(int i = 0;i < list.size();i++){
			        				
			        					sb.append("{"+list.get(i)+"}");
			        					sb.append("{"+list.get(++i)+"}");
			        					sb.append(list2.get(zz++)+ "\n");
			        				
			        			}
			        				System.out.println(sb.toString());
			        			PrintWriter output = new PrintWriter(out);
			        		// Tworz� tablic� z klatkami w klamrach i sklejone napisy	
			        			String[] subtitles = sb.toString().split("\n");
			        		// Zapisuj� wiersz po wierszu w nowym pliku 	
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
				nf.delay(in, out, 25, 1); // fps = 25 kl/s i op�nienie 1 sekunda
				
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
//			// out - plik wyj�ciowy
//			// delay - op�nienie w milisekundach 
//			//framerate (fps) = ilosc klatek/sekunde
    	
	        }
		}




