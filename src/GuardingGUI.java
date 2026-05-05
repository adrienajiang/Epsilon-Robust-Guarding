import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GuardingGUI extends JFrame {
    private final DrawingPanel drawingPanel = new DrawingPanel();

    public GuardingGUI() {
        setTitle("ε-Robust Guarding");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(245, 247, 250));
        topBar.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel title = new JLabel("ε-Robust Guarding of Simple Polygons");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(35, 45, 65));

        JLabel subtitle = new JLabel("Click vertices in order, then toggle guards/witnesses/visibility.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(90, 100, 120));

        JPanel titleBox = new JPanel(new GridLayout(2, 1));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subtitle);
        topBar.add(titleBox, BorderLayout.WEST);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(250, 700));
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setBorder(new EmptyBorder(20, 18, 20, 18));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        JButton guardToggleButton = styledButton("Toggle Guards");
        JButton witnessToggleButton = styledButton("Toggle Witnesses");
        JButton visibilityButton = styledButton("Toggle Visibility Lines");
        JButton clearButton = styledButton("Clear Polygon");
        JButton saveButton = styledButton("Save Polygon");
        JButton loadButton = styledButton("Load Polygon");

        JLabel epsilonLabel = new JLabel("ε = 0.20");
        epsilonLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        epsilonLabel.setForeground(new Color(45, 55, 75));

        JSlider epsilonSlider = new JSlider(0, 100, 20);
        epsilonSlider.setMajorTickSpacing(25);
        epsilonSlider.setPaintTicks(true);
        epsilonSlider.setBackground(Color.WHITE);

        JLabel guardCount = new JLabel("Guards: 0");
        JLabel witnessCount = new JLabel("Witness lower bound: 0");
        JLabel vertexCount = new JLabel("Vertices: 0");

        guardCount.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        witnessCount.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        vertexCount.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        guardToggleButton.addActionListener(e -> {
            drawingPanel.toggleGuards();
            guardCount.setText("Guards: " + drawingPanel.getGuardCount());
        });

        witnessToggleButton.addActionListener(e -> {
            drawingPanel.toggleWitnesses();
            witnessCount.setText("Witness lower bound: " + drawingPanel.getWitnessCount());
        });

        visibilityButton.addActionListener(e -> drawingPanel.toggleVisibilityLines());

        clearButton.addActionListener(e -> {
            drawingPanel.clear();
            guardCount.setText("Guards: 0");
            witnessCount.setText("Witness lower bound: 0");
            vertexCount.setText("Vertices: 0");
        });

        saveButton.addActionListener(e -> drawingPanel.savePolygonToFile());

        loadButton.addActionListener(e -> {
            drawingPanel.loadPolygonFromFile();
            guardCount.setText("Guards: " + drawingPanel.getGuardCount());
            witnessCount.setText("Witness lower bound: " + drawingPanel.getWitnessCount());
            vertexCount.setText("Vertices: " + drawingPanel.getVertexCount());
        });

        epsilonSlider.addChangeListener(e -> {
            double epsilon = epsilonSlider.getValue() / 100.0;
            drawingPanel.setEpsilon(epsilon);
            epsilonLabel.setText(String.format("ε = %.2f", epsilon));
            guardCount.setText("Guards: 0");
            witnessCount.setText("Witness lower bound: 0");
        });

        drawingPanel.setVertexCountListener(count -> vertexCount.setText("Vertices: " + count));

        sidePanel.add(sectionLabel("Controls"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(guardToggleButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(witnessToggleButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(visibilityButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(clearButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(saveButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(loadButton);
        sidePanel.add(Box.createVerticalStrut(25));

        sidePanel.add(sectionLabel("Robustness"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(epsilonLabel);
        sidePanel.add(epsilonSlider);
        sidePanel.add(Box.createVerticalStrut(25));

        sidePanel.add(sectionLabel("Stats"));
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(vertexCount);
        sidePanel.add(Box.createVerticalStrut(5));
        sidePanel.add(guardCount);
        sidePanel.add(Box.createVerticalStrut(5));
        sidePanel.add(witnessCount);

        sidePanel.add(Box.createVerticalGlue());

        JLabel help = new JLabel(
                "<html><b>How to use:</b><br>" +
                "Click vertices in boundary order.<br><br>" +
                "<span style='color:red;'>Red</span> = guards<br>" +
                "<span style='color:green;'>Green</span> = witnesses<br>" +
                "Gray lines = ε-robust visibility.<br><br>" +
                "Save/load format:<br>x y</html>"
        );
        help.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        help.setForeground(new Color(100, 105, 120));
        sidePanel.add(help);

        add(topBar, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
    }

    private JButton styledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.setBackground(new Color(70, 110, 255));
        button.setForeground(Color.WHITE);

        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        return button;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(120, 130, 150));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuardingGUI gui = new GuardingGUI();
            gui.setVisible(true);
        });
    }
}

class DrawingPanel extends JPanel {
    private final List<Point> points = new ArrayList<>();
    private Set<Integer> guards = new LinkedHashSet<>();
    private Set<Integer> witnesses = new LinkedHashSet<>();

    private double epsilon = 0.2;

    private boolean showVisibilityLines = false;
    private boolean showGuards = false;
    private boolean showWitnesses = false;

    private VertexCountListener vertexCountListener;

    public DrawingPanel() {
        setBackground(new Color(248, 250, 252));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(new Point(e.getX(), e.getY()));
                guards.clear();
                witnesses.clear();

                if (vertexCountListener != null) {
                    vertexCountListener.onVertexCountChanged(points.size());
                }

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g2);
        drawPolygon(g2);

        if (showVisibilityLines) {
            drawVisibilityLines(g2);
        }

        drawVertices(g2);
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(230, 235, 242));

        for (int x = 0; x < getWidth(); x += 25) {
            g2.drawLine(x, 0, x, getHeight());
        }

        for (int y = 0; y < getHeight(); y += 25) {
            g2.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawPolygon(Graphics2D g2) {
        if (points.size() < 2) return;

        int[] xs = new int[points.size()];
        int[] ys = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            xs[i] = (int) points.get(i).x;
            ys[i] = (int) points.get(i).y;
        }

        if (points.size() >= 3) {
            g2.setColor(new Color(100, 130, 255, 35));
            g2.fillPolygon(xs, ys, points.size());
        }

        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(40, 55, 85));

        for (int i = 0; i < points.size() - 1; i++) {
            Point a = points.get(i);
            Point b = points.get(i + 1);
            g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
        }

        if (points.size() >= 3) {
            Point first = points.get(0);
            Point last = points.get(points.size() - 1);
            g2.drawLine((int) last.x, (int) last.y, (int) first.x, (int) first.y);
        }
    }

    private void drawVisibilityLines(Graphics2D g2) {
        if (points.size() < 3) return;

        Polygon polygon = new Polygon(points);
        g2.setStroke(new BasicStroke(1));

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (VisibilityChecker.epsilonRobustVisibility(polygon, i, j, epsilon)) {
                    Point a = points.get(i);
                    Point b = points.get(j);

                    g2.setColor(new Color(120, 120, 120, 90));
                    g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
                }
            }
        }
    }

    private void drawVertices(Graphics2D g2) {
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);

            if (showGuards && guards.contains(i)) {
                g2.setColor(new Color(255, 80, 90, 60));
                g2.fillOval((int) p.x - 22, (int) p.y - 22, 44, 44);

                g2.setColor(new Color(255, 80, 90));
                g2.fillOval((int) p.x - 10, (int) p.y - 10, 20, 20);
            } else if (showWitnesses && witnesses.contains(i)) {
                g2.setColor(new Color(40, 180, 120, 60));
                g2.fillOval((int) p.x - 18, (int) p.y - 18, 36, 36);

                g2.setColor(new Color(40, 180, 120));
                g2.fillOval((int) p.x - 8, (int) p.y - 8, 16, 16);
            } else {
                g2.setColor(new Color(70, 110, 255));
                g2.fillOval((int) p.x - 7, (int) p.y - 7, 14, 14);
            }

            g2.setColor(Color.WHITE);
            g2.fillOval((int) p.x - 3, (int) p.y - 3, 6, 6);

            g2.setColor(new Color(35, 45, 65));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.drawString(String.valueOf(i), (int) p.x + 12, (int) p.y - 10);
        }
    }

    private void computeIfNeeded() {
        if (points.size() < 3) {
            return;
        }

        Polygon polygon = new Polygon(points);

        if (guards.isEmpty()) {
            guards = GuardSolver.greedyGuards(polygon, epsilon);
        }

        if (witnesses.isEmpty()) {
            witnesses = WitnessSolver.greedyWitnesses(polygon, epsilon);
        }
    }

    public void clear() {
        points.clear();
        guards.clear();
        witnesses.clear();
        repaint();
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        guards.clear();
        witnesses.clear();
        showGuards = false;
        showWitnesses = false;
        repaint();
    }

    public int getGuardCount() {
        return guards.size();
    }

    public int getWitnessCount() {
        return witnesses.size();
    }

    public int getVertexCount() {
        return points.size();
    }

    public void setVertexCountListener(VertexCountListener listener) {
        this.vertexCountListener = listener;
    }

    public void toggleVisibilityLines() {
        showVisibilityLines = !showVisibilityLines;
        repaint();
    }

    public void toggleGuards() {
        if (points.size() < 3) {
            JOptionPane.showMessageDialog(this, "Please add at least 3 vertices.");
            return;
        }

        computeIfNeeded();
        showGuards = !showGuards;
        repaint();
    }

    public void toggleWitnesses() {
        if (points.size() < 3) {
            JOptionPane.showMessageDialog(this, "Please add at least 3 vertices.");
            return;
        }

        computeIfNeeded();
        showWitnesses = !showWitnesses;
        repaint();
    }

    public void savePolygonToFile() {
        if (points.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No polygon to save.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Polygon");

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (Point p : points) {
                    writer.println(p.x + " " + p.y);
                }

                JOptionPane.showMessageDialog(this, "Polygon saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving polygon: " + e.getMessage());
            }
        }
    }

    public void loadPolygonFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Polygon");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            List<Point> loadedPoints = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.isEmpty()) continue;

                    String[] parts = line.split("\\s+");

                    if (parts.length != 2) {
                        throw new IOException("Invalid line: " + line);
                    }

                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);

                    loadedPoints.add(new Point(x, y));
                }

                if (loadedPoints.size() < 3) {
                    JOptionPane.showMessageDialog(this, "File must contain at least 3 points.");
                    return;
                }

                points.clear();
                points.addAll(loadedPoints);
                guards.clear();
                witnesses.clear();
                showGuards = false;
                showWitnesses = false;

                if (vertexCountListener != null) {
                    vertexCountListener.onVertexCountChanged(points.size());
                }

                repaint();

                JOptionPane.showMessageDialog(this, "Polygon loaded successfully.");
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error loading polygon: " + e.getMessage());
            }
        }
    }
}

interface VertexCountListener {
    void onVertexCountChanged(int count);
}