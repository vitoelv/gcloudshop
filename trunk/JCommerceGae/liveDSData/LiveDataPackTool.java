import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LiveDataPackTool {
    static String dataFileName = "mydata.txt";
    static String filesFolderName = "files";
    
    public static void main(String[] args) {
        try {
            System.out.println("args: ");
            for(int i=0;i<args.length;i++) {
                String arg = args[i];
                System.out.println("arg["+i+"]="+arg);
            }
            
            String rootPath = "D:/JCommerce/JCommerceGae/liveDSData/";
            String outFileName = "data.zip";
            
            if(args.length!=2) {
                usage();
                return;
            }else {
                rootPath = args[0];
                if((rootPath.endsWith("/") || rootPath.endsWith("\\"))== false) {
                    rootPath = rootPath+"/";
                }
                outFileName = args[1];
            }


            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(rootPath + outFileName));

            // data file first
            zip(out, new File(rootPath+dataFileName), dataFileName);
            
            // followed by folder
            zip(out, new File(rootPath+filesFolderName), filesFolderName);
            out.flush();
            out.close();
            System.out.println("Done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void usage() {
        System.out.println("usage: java LiveDataPackTool {rootPath} {outFileName}");
    }
        public static void zip(ZipOutputStream out, File f, String base) throws Exception {
            
            if (f.isDirectory()) {
                if(f.getName().equals(".svn")) {
                    return;
                }
                File[] fl = f.listFiles();
                out.putNextEntry(new ZipEntry(base + "/"));
                base = base.length() == 0 ? "" : base + "/";
                for (int i = 0; i < fl.length; i++) {
                    zip(out, fl[i], base + fl[i].getName());
                }
            } else {
                if(f.getName().endsWith(".zip")) {
                    return;
                }
                
                if(isAlreadyCompressed(f.getName())) {
                    out.setLevel(ZipOutputStream.STORED);
                }else {
                    out.setLevel(ZipOutputStream.DEFLATED);
                }
                out.putNextEntry(new ZipEntry(base));
                FileInputStream in = new FileInputStream(f);
                
                // TODO optimize via buffer size
//                IOUtils.copy(in, out);
                byte[] buf = new byte[1024];
                while(true) {
                    int i = in.read(buf);
                    if(i==-1) {
                        break;
                    }
                    out.write(buf, 0, i);
                }
                in.close();
            }
        }
        
        private static boolean isAlreadyCompressed(String fileName) {
            return !fileName.endsWith("txt");
        }
}
