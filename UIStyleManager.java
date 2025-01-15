package 헬스파트너;

import javax.swing.*;
import java.awt.*;

public class UIStyleManager {

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(42, 42, 42));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(72, 72, 72));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(42, 42, 42));
            }
        });

        return button;
    }

    public JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setBackground(Color.DARK_GRAY);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // 버퍼 초기화를 위한 의미 없는 입력
        field.setText(" ");
        field.setText("");

        return field;
    }

    public JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        styleTextField(field);

        return field;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(Color.DARK_GRAY);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        field.setCaretColor(Color.WHITE);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.selectAll();
            }
        });
    }
}