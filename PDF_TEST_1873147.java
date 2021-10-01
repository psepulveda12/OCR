/*
2	 * Licensed to the Apache Software Foundation (ASF) under one or more
3	 * contributor license agreements.  See the NOTICE file distributed with
4	 * this work for additional information regarding copyright ownership.
5	 * The ASF licenses this file to You under the Apache License, Version 2.0
6	 * (the "License"); you may not use this file except in compliance with
7	 * the License.  You may obtain a copy of the License at
8	 *
9	 *      http://www.apache.org/licenses/LICENSE-2.0
10	 *
11	 * Unless required by applicable law or agreed to in writing, software
12	 * distributed under the License is distributed on an "AS IS" BASIS,
13	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
14	 * See the License for the specific language governing permissions and
15	 * limitations under the License.
16	 */

	
	import java.io.File;
	import java.io.IOException;
	
	import org.apache.pdfbox.pdmodel.PDDocument;
	import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
	import org.apache.pdfbox.text.PDFTextStripper;
	
	/**
	 * This is a simple text extraction example to get started. For more advance usage, see the
	 * ExtractTextByArea and the DrawPrintTextLocations examples in this subproject, as well as the
	 * ExtractText tool in the tools subproject.
	 *
	 * @author Tilman Hausherr
	 */
	public class PDF_TEST_1873147
	{
	
	    /**
	     * This will print the documents text page by page.
	     *
	     * @param args The command line arguments.
	     *
	     * @throws IOException If there is an error parsing or extracting the document.
	     */
	    public static void main(String[] args) throws IOException
	    {
	
	        try (PDDocument document = PDDocument.load(new File("file.pdf")))
	        {
	            AccessPermission ap = document.getCurrentAccessPermission();
	            if (!ap.canExtractContent())
	            {
	                throw new IOException("You do not have permission to extract text");
	            }
	
	            PDFTextStripper stripper = new PDFTextStripper();
	
	            // This example uses sorting, but in some cases it is more useful to switch it off,
	            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
	            stripper.setSortByPosition(true);
	
	            for (int p = 1; p <= document.getNumberOfPages(); ++p)
	            {
	                // Set the page interval to extract. If you don't, then all pages would be extracted.
	                stripper.setStartPage(p);
	                stripper.setEndPage(p);
	
	                // let the magic happen
	                String text = stripper.getText(document);
	
	                // do some nice output with a header
	                String pageStr = String.format("page %d:", p);
	                System.out.println(pageStr);
	                for (int i = 0; i < pageStr.length(); ++i)
	                {
	                    System.out.print("-");
	                }
	                System.out.println();
	                System.out.println(text.trim());
	                System.out.println();
	
	                // If the extracted text is empty or gibberish, please try extracting text
	                // with Adobe Reader first before asking for help. Also read the FAQ
	                // on the website: 
	                // https://pdfbox.apache.org/2.0/faq.html#text-extraction
	            }
	        }
	    }
	

	}