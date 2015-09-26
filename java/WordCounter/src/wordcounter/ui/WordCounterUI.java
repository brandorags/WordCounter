/**
 * Copyright Brandon Ragsdale (c) 2015 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package wordcounter.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import wordcounter.backend.WordCounter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class WordCounterUI {

	private JFrame frmWordcounter;
	private JTextField locationTextField;
	private JTextField keyWordTextField;
	private JTextArea wordCounterOutputTextArea = new JTextArea();
	private WordCounter wordCounter = new WordCounter();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordCounterUI window = new WordCounterUI();
					window.frmWordcounter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WordCounterUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWordcounter = new JFrame();
		frmWordcounter.setTitle("WordCounter");
		frmWordcounter.setBounds(100, 100, 616, 375);
		frmWordcounter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel locationLabel = new JLabel("Location");
		
		locationTextField = new JTextField();
		locationTextField.setColumns(10);
		
		JLabel keyWordLabel = new JLabel("Key Word");
		
		keyWordTextField = new JTextField();
		keyWordTextField.setColumns(10);
		
		JButton searchButton = new JButton("Search...");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser searchBox = new JFileChooser();
				searchBox.setCurrentDirectory(new File(System.getProperty("user.home")));
				searchBox.showOpenDialog(frmWordcounter);
				
				String path = searchBox.getSelectedFile().getAbsolutePath();
				locationTextField.setText(path);
				
				wordCounter.setDirectoryOrFile(searchBox.getSelectedFile()); 
			}
		});
		
		JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordCounter.setWord(keyWordTextField.getText());
				
				try {
					if (!wordCounter.wordCounterFieldsAreSet()) {
						return;
					}
					
					runButton.setEnabled(false);
					searchButton.setEnabled(false);
					wordCounterOutputTextArea.setText("");
					
					List<String> textAreaOutput = wordCounter.getWordOccurences();
					for (String line : textAreaOutput) {
						wordCounterOutputTextArea.append(line);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(new JFrame(), ex, "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				runButton.setEnabled(true);
				searchButton.setEnabled(true);
			}
		});
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frmWordcounter.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(454, Short.MAX_VALUE)
					.addComponent(quitButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(runButton)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(wordCounterOutputTextArea, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(locationLabel)
								.addComponent(keyWordLabel))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(keyWordTextField)
								.addComponent(locationTextField, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(searchButton)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(locationLabel)
						.addComponent(locationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchButton))
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(keyWordLabel)
						.addComponent(keyWordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wordCounterOutputTextArea, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(runButton)
						.addComponent(quitButton))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		frmWordcounter.getContentPane().setLayout(groupLayout);
	}
}
