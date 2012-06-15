package com.efreak1996.BukkitManager.Swing.Local.Frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ConsoleOutput extends JTextPane {

	private JScrollPane scrollPane;
	private boolean textSelected = false;

	public ConsoleOutput() {
		scrollPane = new JScrollPane(this);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setContentType("charset=UTF-8");
		setEditable(false);
		((DefaultCaret)getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		getDocument().addDocumentListener(new ScrollingDocumentListener(this));
	}

	public void append(Color color, String text) {
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setForeground(sas, color);
		Document doc = getDocument();
		try {
			doc.insertString(doc.getLength(), text, sas);
		} catch (BadLocationException e) {
		}
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

    private class ScrollingDocumentListener implements DocumentListener {

        private ConsoleOutput consoleOutput;

        public ScrollingDocumentListener(ConsoleOutput arg1ConsoleOutput) {
        	consoleOutput = arg1ConsoleOutput;
        }

        public void changedUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        public void insertUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        public void removeUpdate(DocumentEvent e) {
            maybeScrollToBottom();
        }

        private void maybeScrollToBottom() {
            JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
            boolean scrollBarAtBottom = isScrollBarFullyExtended(scrollBar);
            if(scrollBarAtBottom) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                scrollToBottom(consoleOutput);
                            }
                        });
                    }
                });
            }
        }
    }

    private static boolean isScrollBarFullyExtended(JScrollBar scrollBar) {
        BoundedRangeModel model = scrollBar.getModel();
        return (model.getExtent() + model.getValue()) == model.getMaximum();
    }

    private static void scrollToBottom(JComponent component) {
        Rectangle visibleRect = component.getVisibleRect();
        visibleRect.y = component.getHeight() - visibleRect.height;
        component.scrollRectToVisible(visibleRect);
    }
}