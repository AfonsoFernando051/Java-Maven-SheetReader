package importable.run;

// Imports do SWT
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.model.CompanyAsset;
import importable.model.Customer;
import importable.model.Employee;
import importable.model.Inventory;
import importable.model.Order;
import importable.model.Product;
import importable.model.Shipment;
import importable.model.Supplier;
import importable.model.Task;
import importable.model.Warehouse;
import importable.old.OldAssetImporter;
import importable.old.OldCustomerImporter;
import importable.old.OldEmployeeImporter;
import importable.old.OldInventoryImporter;
import importable.old.OldOrderImporter;
import importable.old.OldProductImporter;
import importable.old.OldShipmentImporter;
import importable.old.OldSupplierImporter;
import importable.old.OldTaskImporter;
import importable.old.OldWarehouseImporter;
import importable.service.ImportService;
import importable.service.SheetImportConfigManager;
import importable.service.factory.ImportServiceFactory;

/**
 * A graphical SWT interface that acts as a launcher
 * for the import methods.
 */
public class SWTImportRunner {

    private static Text outputArea;

    public static void main(String[] args) {
        // Basic SWT setup
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Import Method Runner (SWT)");
        
        createUI(shell, display); // Create buttons and text area

        shell.setSize(700, 500); // Set a reasonable initial size
        shell.open();

        // Main event loop (keeps window open)
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    /**
     * Creates the UI widgets inside the main Shell.
     */
    private static void createUI(Shell shell, Display display) {
        shell.setLayout(new GridLayout(1, false));

        // 1. Composite for the buttons
        Composite buttonComposite = new Composite(shell, SWT.NONE);
        buttonComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
        buttonComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

        Button btnWithPatterns = new Button(buttonComposite, SWT.PUSH);
        btnWithPatterns.setText("  Run WITH Design Patterns  ");

        Button btnWithoutPatterns = new Button(buttonComposite, SWT.PUSH);
        btnWithoutPatterns.setText("  Run WITHOUT Design Patterns  ");

        // 2. Text Area for Console Output
        outputArea = new Text(shell, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
        outputArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        // "Console" style (optional, but nice)
        Color black = display.getSystemColor(SWT.COLOR_BLACK);
        Color green = display.getSystemColor(SWT.COLOR_GREEN);
        outputArea.setBackground(black);
        outputArea.setForeground(green);

        // 3. Add Listeners (click events)
        
        // Listener for Button 1
        btnWithPatterns.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Run the import on a new Thread to avoid UI freeze
                runImportTask(() -> readDataWithDesignPatterns());
            }
        });

        // Listener for Button 2
        btnWithoutPatterns.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Run the import on a new Thread to avoid UI freeze
                runImportTask(() -> readDataWithoutDesignPatterns());
            }
        });
    }

    /**
     * Executes an import task on a separate thread and
     * captures its console output.
     * @param importTask The task to be executed (e.g., this::readData...)
     */
    private static void runImportTask(Runnable importTask) {
        outputArea.setText("Running import... please wait.");
        
        new Thread(() -> {
            // Capture console output
            String output = captureConsoleOutput(importTask);
            
            // Update the UI back on the main thread
            outputArea.getDisplay().asyncExec(() -> {
                outputArea.setText(output);
                // Scroll to the end of the text
                outputArea.setSelection(outputArea.getText().length()); 
            });
        }).start();
    }

    /**
     * Redirects System.out and System.err to a String,
     * executes the task, and then restores the original streams.
     * @param task The (Runnable) task to be executed.
     * @return A String containing all console output.
     */
    private static String captureConsoleOutput(Runnable task) {
        // Save original streams
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);

        // Redirect output and error
        System.setOut(newOut);
        System.setErr(newOut);

        try {
            task.run(); // Execute the method (e.g., readDataWithDesignPatterns)
        } catch (Exception e) {
            e.printStackTrace(newOut); // Capture exceptions too
        } finally {
            // Ensure streams are restored
            System.out.flush();
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        return baos.toString();
    }
    
    // ===================================================================
    //  LOGIC METHODS (Moved from OriginalMain)
    // ===================================================================

    /**
     * Runs the import process using Factory and Strategy patterns.
     */
    private static void readDataWithDesignPatterns() {
        System.out.println("--- Using Factory and Strategy patterns to decouple the import logic ---");
        for (SheetTypeEnum sheet : SheetTypeEnum.values()) {
            // Use the translated class and variable names
            SheetImportConfigManager sheetManager = new SheetImportConfigManager();
            ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
            HashMap<SheetTypeEnum, SheetModel> sheetModel = service.generateSheetModel(sheet);
            
            // Use the translated method name
            sheetManager.setSheetModels(sheetModel); 
            
            // Use the translated method name
            HashMap<SheetTypeEnum, ?> result = service.importAndInsertDataFromSheets(sheetManager, service.getBytesManager(sheet));
            
            ArrayList<?> arrayList = (ArrayList<?>) result.get(sheet);
            System.out.println("--- Imported " + arrayList.size() + " records from " + sheet.name() + " ---");
            for (Object object : arrayList) {
                System.out.println(object.toString());
            }
            System.out.println();
        }
    }

    /**
     * Runs the import process using rigid, hardcoded static methods.
     */
    private static void readDataWithoutDesignPatterns() {
        System.out.println("--- Using static methods with hardcoded values. Very rigid. ---");
        try {
            // Use this class name to get the resource
            InputStream customerStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("customers.xlsx");
            byte[] customerFile = customerStream.readAllBytes();
            // Use the translated method name
            List<Customer> customers = OldCustomerImporter.importSheetData("0", true, "A", "B", "D", "F", "G",
                    customerFile);
            System.out.println("\nCustomers imported: " + customers.size());
            for (Object c : customers) {
                System.out.println(c);
            }

            InputStream productStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("products.xlsx");
            byte[] productFile = productStream.readAllBytes();
            List<Product> products = OldProductImporter.importSheetData("0", true, "A", "B", "D", "C",
                    productFile);
            System.out.println("\nProducts imported: " + products.size());
            for (Object p : products) {
                System.out.println(p);
            }
            System.out.println();
            
            InputStream supplierStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("suppliers.xlsx");
            byte[] supplierFile = supplierStream.readAllBytes();
            List<Supplier> suppliers = OldSupplierImporter.importSheetData("0", true, "A", "B", "D", "E", "G",
                    supplierFile);

            System.out.println("\nSuppliers imported: " + suppliers.size());
            for (Object s : suppliers) {
                System.out.println(s);
            }

            InputStream employeeStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("employees.xlsx");
            byte[] employeeFile = employeeStream.readAllBytes();
            List<Employee> employees = OldEmployeeImporter.importSheetData("0", true, "A", "B", "C", "D", "E",
                    "F", employeeFile);

            System.out.println("\nEmployees imported: " + employees.size());
            for (Object e : employees) {
                System.out.println(e);
            }

            InputStream orderStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("orders.xlsx");
            byte[] orderFile = orderStream.readAllBytes();
            List<Order> orders = OldOrderImporter.importSheetData("0", true, "A", "B", "C", "D", "E", orderFile);

            System.out.println("\nOrders imported: " + orders.size());
            for (Object o : orders) {
                System.out.println(o);
            }

            InputStream inventoryStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("inventory.xlsx");
            byte[] inventoryFile = inventoryStream.readAllBytes();
            List<Inventory> inventory = OldInventoryImporter.importSheetData("0", true, "A", "B", "C", "D",
                    inventoryFile);

            System.out.println("\nInventory items imported: " + inventory.size());
            for (Object i : inventory) {
                System.out.println(i);
            }
            InputStream shipmentStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("shipments.xlsx");
            byte[] shipmentFile = shipmentStream.readAllBytes();
            List<Shipment> shipments = OldShipmentImporter.importSheetData("0", true, "A", "B", "C", "D", "E",
                    "F", shipmentFile);

            System.out.println("\nShipments imported: " + shipments.size());
            for (Shipment s : shipments) {
                System.out.println(s);
            }
            InputStream assetStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("assets.xlsx");
            byte[] assetFile = assetStream.readAllBytes();
            List<CompanyAsset> assets = OldAssetImporter.importSheetData("0", true, "A", "B", "C", "D", "E", "F",
                    assetFile);

            System.out.println("\nCompany Assets imported: " + assets.size());
            for (CompanyAsset a : assets) {
                System.out.println(a);
            }

            InputStream taskStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("tasks.xlsx");
            byte[] taskFile = taskStream.readAllBytes();
            List<Task> tasks = OldTaskImporter.importSheetData("0", true, "A", "B", "C", "D", "E", "F", taskFile);

            System.out.println("\nTasks imported: " + tasks.size());
            for (Task t : tasks) {
                System.out.println(t);
            }
            InputStream warehouseStream = SWTImportRunner.class.getClassLoader().getResourceAsStream("warehouses.xlsx");
            byte[] warehouseFile = warehouseStream.readAllBytes();
            List<Warehouse> warehouses = OldWarehouseImporter.importSheetData("0", true, "A", "B", "C", "D",
                    warehouseFile);

            System.out.println("\nWarehouses imported: " + warehouses.size());
            for (Warehouse w : warehouses) {
                System.out.println(w);
            }

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}