package com.unw.crypto;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.chart.BollingerBandsChart;
import com.unw.crypto.chart.CandleChart;
import com.unw.crypto.chart.EMARsiChart;
import com.unw.crypto.chart.EMARsiStoChart;
import com.unw.crypto.chart.EMAStoChart;
import com.unw.crypto.chart.MovingAverageChart;
import com.unw.crypto.chart.SimpleClosedPriceChart;
import com.unw.crypto.chart.TickUtil;
import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.model.Tick;
import com.unw.crypto.strategy.StrategyPanel;
import com.unw.crypto.ui.TabUtil;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.ta4j.core.TimeSeries;

/**
 * the app for the UI Application
 *
 */
//@SpringBootApplication
public class SpringBootAppUI extends Application {

    private ConfigurableApplicationContext context;
    private BorderPane bp;
    private Scene scene;
    private BorderPane bpBottom;
    private TextField tfBottomLeft = new TextField();
    private TextField tfBottomRight = new TextField();
    private ProgressBar progressBar = new ProgressBar();
    private DatePicker from;
    private DatePicker until;
    private ComboBox<Currency> cmbCurrency;
    private ComboBox<Exchange> cmbExchange;
    private ComboBox<BarDuration> barDuration;
    private int barDurationInMinutes;
    //charts
    private CandleChart candleChart;
    private SimpleClosedPriceChart simpleClosedPriceChart;
    private MovingAverageChart movingAverageChart;
    private EMARsiChart emaRsiChart;
    private EMAStoChart emaStoChart;
    private EMARsiStoChart emaRsiStoChart;
    private BollingerBandsChart bollingerChart;
    //strategy
    private StrategyPanel strategyPanel;
    //
    private TimeSeriesDBLoader timeSeriesDBLoader;
    // series and ticks for the selected period
    private TimeSeries series;
    private List<Tick> ticks;
    // series for live testing, 2 month before series 
    private TimeSeries preSeries;
    // month to load  before the period, should be 2 to be able to build MA200 for 10 Min Bar, for faster testing it is reduced to one 
    private static final int PRE_MONTH = 1;

    public static void main(String[] args) {
        launch(SpringBootAppUI.class, args);
    }

    @Override
    public void stop() throws Exception {
        context.stop();
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(SpringBootAppUI.class);
        // dataLoader = context.getBean(DataLoaderDB.class);
        timeSeriesDBLoader = context.getBean(TimeSeriesDBLoader.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUi(primaryStage);
    }

    private void initUi(Stage primaryStage) {
        System.setProperty("java.awt.headless", "false");
        java.awt.Toolkit.getDefaultToolkit();
        BorderPane root = createBorderPane();
        createToolbar(root);
        //
        initTabPane(root);
        //
        createBottomBar(root);

        scene = new Scene(root, Config.WIDTH, Config.HEIGHT);
        primaryStage.setTitle("TradingBot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createBorderPane() {
        bp = new BorderPane();
        return bp;
    }

    private void createToolbar(BorderPane root) {
        ToolBar tb = new ToolBar();
        initButtons(tb);
        initComboAndDate(tb);
        root.setTop(tb);
    }

    private void initButtons(ToolBar tb) {
        Button bttRecord = new Button();
        bttRecord.setText("Load");
        bttRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // recordData();
                System.out.println("Load Data");
                loadData();
            }
        });
        Button bttStop = new Button();
        bttStop.setText("Refresh");
        bttStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Refresh");
                refreshData();
            }
        });

        Button bttRead = new Button();
        bttRead.setText("Cancel");
        bttRead.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Cancel");
                //readData();
            }
        });

        tb.getItems().add(bttRecord);
        tb.getItems().add(bttStop);
        tb.getItems().add(bttRead);

    }

    private void initComboAndDate(ToolBar tb) {

        cmbCurrency = new ComboBox();
        cmbCurrency.getItems().setAll(Currency.values());
        cmbCurrency.setValue(Currency.BTC);
        cmbExchange = new ComboBox();
        cmbExchange.getItems().setAll(Exchange.values());
        cmbExchange.setValue(Exchange.COINBASE);

        from = new DatePicker();
        //from.setValue(LocalDate.now().minusMonths(3));
        from.setValue(LocalDate.now().minusMonths(8));
        until = new DatePicker();
        //until.setValue(LocalDate.now());
        until.setValue(LocalDate.now().minusMonths(7));

        barDuration = new ComboBox<>();
        barDuration.getItems().setAll(BarDuration.values());
        barDuration.setValue(BarDuration.TWO_HOURS);
        barDurationInMinutes = barDuration.getValue().getIntValue();

        tb.getItems().add(cmbCurrency);
        tb.getItems().add(cmbExchange);
        tb.getItems().add(from);
        tb.getItems().add(until);
        tb.getItems().add(barDuration);
    }

    private void createBottomBar(BorderPane root) {
        bpBottom = new BorderPane();
        tfBottomLeft.setPrefWidth(550);
        tfBottomRight.setPrefWidth(550);
        progressBar.setProgress(0d);
        progressBar.setPrefWidth(300);
        bpBottom.setCenter(progressBar);
        bpBottom.setLeft(tfBottomLeft);
        bpBottom.setRight(tfBottomRight);
        root.setBottom(bpBottom);
    }

    private void initTabPane(BorderPane root) {
        barDurationInMinutes = barDuration.getValue().getIntValue();
        System.out.println("Loading ticks");
        ticks = timeSeriesDBLoader.loadTicksWithParams(from.getValue(), until.getValue(), cmbCurrency.getValue(), cmbExchange.getValue(), barDurationInMinutes);
        //series = timeSeriesDBLoader.loadSeriesWithParams(from.getValue(), until.getValue(), cmbCurrency.getValue(), cmbExchange.getValue(), barDurationInMinutes);
        if (ticks.isEmpty()) {
            System.out.println("no data found" + cmbCurrency.getValue() + " " + cmbExchange.getValue() + " ; please reload with different params");
            // create a dummy tick to avoid NPE
            ticks.add(TickUtil.createDummyTick(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue()));
        }
        System.out.println("Loading series by ticks");
        series = timeSeriesDBLoader.loadSeriesByTicks(ticks, barDurationInMinutes);
        // loading the pre-series ( 2 month before from)
        LocalDate preFrom = from.getValue().minusMonths(PRE_MONTH);
        System.out.println("Loading preSeries");
        preSeries = timeSeriesDBLoader.loadSeriesWithParams(preFrom, from.getValue(), cmbCurrency.getValue(), cmbExchange.getValue(), barDurationInMinutes);

        String txtLeft = createBottomTextLeft();
        String txtRight = createBottomTextRight();
        setBottomText(txtLeft, txtRight);

        TabPane tabPane = new TabPane();
        // charts
        candleChart = new CandleChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        tabPane.getTabs().add(TabUtil.createChartTab(candleChart, "Candle"));
        simpleClosedPriceChart = new SimpleClosedPriceChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        tabPane.getTabs().add(TabUtil.createChartTab(simpleClosedPriceChart, "ClosedPrice"));
        movingAverageChart = new MovingAverageChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        movingAverageChart.setBarDuration(barDuration.getValue());
        tabPane.getTabs().add(TabUtil.createChartTab(movingAverageChart, "MovingAverage"));
        emaRsiChart = new EMARsiChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        tabPane.getTabs().add(TabUtil.createChartTab(emaRsiChart, "EMA RSI"));
        emaStoChart = new EMAStoChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        tabPane.getTabs().add(TabUtil.createChartTab(emaStoChart, "EMA STO"));
        emaRsiStoChart = new EMARsiStoChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        tabPane.getTabs().add(TabUtil.createChartTab(emaRsiStoChart, "EMA RSI/STO"));
        bollingerChart = new BollingerBandsChart(series, tabPane, cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());
        tabPane.getTabs().add(TabUtil.createChartTab(bollingerChart, "Bollinger"));
        //strategy
        strategyPanel = new StrategyPanel(ticks, series, preSeries, progressBar, context);
        strategyPanel.setBarDuration(barDuration.getValue());
        tabPane.getTabs().add(TabUtil.createStrategyTab(strategyPanel, "Strategy"));
        //root.setCenter(sc);
        root.setCenter(tabPane);
    }

    private void setBottomText(String textLeft, String textRight) {
        tfBottomLeft.setText(textLeft);
        tfBottomRight.setText(textRight);
    }

    private String createBottomTextRight() {
        if (timeSeriesDBLoader.loadFirstEntry(cmbCurrency.getValue(), cmbExchange.getValue()) == null) {
            return "- ";
        }
        return "Total data from " + timeSeriesDBLoader.loadFirstEntry(cmbCurrency.getValue(), cmbExchange.getValue()).getTradeTime()
                + " until " + timeSeriesDBLoader.loadLastEntry(cmbCurrency.getValue(), cmbExchange.getValue()).getTradeTime() + "(" + cmbCurrency.getValue().toString() + ")";
    }

    private String createBottomTextLeft() {
        if (series.isEmpty()) {
            return "- ";
        }
        return "Loaded " + series.getBarCount() + " Bars from " + series.getFirstBar().getBeginTime().toLocalDateTime()
                + " until " + series.getLastBar().getBeginTime().toLocalDateTime() + "(" + cmbCurrency.getValue().toString() + ")";
    }

    private void loadData() {
        progressBar.setProgress(0.1d);
        System.out.println(" Load data for " + from.getValue() + " " + until.getValue() + " " + cmbCurrency.getValue() + " " + cmbExchange.getValue());
        barDurationInMinutes = barDuration.getValue().getIntValue();

        ticks = timeSeriesDBLoader.loadTicksWithParams(from.getValue(), until.getValue(), cmbCurrency.getValue(), cmbExchange.getValue(), barDurationInMinutes);
        progressBar.setProgress(0.3d);
        series = timeSeriesDBLoader.loadSeriesByTicks(ticks, barDurationInMinutes);
        progressBar.setProgress(0.6d);
        // loading the pre-series ( 2 month before from)
        LocalDate preFrom = from.getValue().minusMonths(PRE_MONTH);
        System.out.println("load preseries");
        preSeries = timeSeriesDBLoader.loadSeriesWithParams(preFrom, from.getValue(), cmbCurrency.getValue(), cmbExchange.getValue(), barDurationInMinutes);
        progressBar.setProgress(0.9d);
        String txtLeft = createBottomTextLeft();
        String txtRight = createBottomTextRight();
        setBottomText(txtLeft, txtRight);

        refreshData();
        progressBar.setProgress(1d);
    }
    private void refreshData(){
        candleChart.setSeries(series);
        candleChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        simpleClosedPriceChart.setSeries(series);
        simpleClosedPriceChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        movingAverageChart.setBarDuration(barDuration.getValue());
        movingAverageChart.setSeries(series);
        movingAverageChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        emaRsiChart.setSeries(series);
        emaRsiChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        emaStoChart.setSeries(series);
        emaStoChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        emaRsiStoChart.setSeries(series);
        emaRsiStoChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        bollingerChart.setSeries(series);
        bollingerChart.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());

        strategyPanel.setBarDuration(barDuration.getValue());
        strategyPanel.setSeries(series);
        strategyPanel.setPreSeries(preSeries);
        strategyPanel.setTicks(ticks);
        strategyPanel.reload(cmbCurrency.getValue().getStringValue(), cmbExchange.getValue().getStringValue());        
    }
}
