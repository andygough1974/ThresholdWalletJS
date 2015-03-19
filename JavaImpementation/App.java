/**
 * Hello world!
 *
 */
package io.microwork._2fa;
//package org.multibit.viewsystem.swing.action;

/*
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bouncycastle.util.Arrays;

import threshold.mr04.Alice;
import threshold.mr04.SignatureTest;
import threshold.mr04.data.PublicParameters;

import com.google.bitcoin.core.MakeCertificate;
import com.google.bitcoin.core.RemoteECKey;
import com.google.bitcoin.core.Utils;
import com.google.bitcoin.core.Wallet;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;
*/

public class App 
{
    public void createNewWallet(String newWalletFilename) {
/*        String message;
        if (new File(newWalletFilename).isDirectory()) {
            message = controller.getLocaliser().getString("createNewWalletAction.walletFileIsADirectory",
                    new Object[] { newWalletFilename });
            log.debug(message);
            MessageManager.INSTANCE.addMessage(new Message(message));
            return;
        }

        // If the filename has no extension, put on the wallet extension.
        if (!newWalletFilename.contains(".")) {
             newWalletFilename = newWalletFilename + "." + BitcoinModel.WALLET_FILE_EXTENSION;
        }

        File newWalletFile = new File(newWalletFilename);
        
        boolean theWalletWasNotOpenedSuccessfully = false;

        try {
            {
                // Create a new wallet - protobuf.2 initially for backwards compatibility.
                Wallet newWallet = new Wallet(this.bitcoinController.getModel().getNetworkParameters());
                
                SecureRandom prGen = new SecureRandom();
                byte[] oneTimePass = new byte[256];
                prGen.nextBytes(oneTimePass);
                
                SignatureTest t = new SignatureTest(2);
                PublicParameters params = new PublicParameters(SignatureTest.CURVE, t.nHat, t.kPrime, t.h1, t.h2,
                        t.alicesPallierPubKey, t.otherPallierPubKey);
                Alice alice = new Alice(t.aliceShare, t.publicKey, new SecureRandom(), t.paillier, params);
                
                File keystore = new File(KEYSTORE_FILENAME);
                
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                KeyStore ks = KeyStore.getInstance("BKS");
                
                if (!keystore.exists()) {
        			MakeCertificate.generateSelfSignedCertificate("compTLSCert", keystore, KEYSTORE_PASSWORD);
        		}
                
                ks.load(new FileInputStream(keystore), KEYSTORE_PASSWORD.toCharArray());
                
                X509Certificate cert = (X509Certificate) ks.getCertificate("compTLSCert");
                byte[] certBytes = cert.getEncoded();
                System.out.println("cert is " + certBytes.length + " bytes");
                
                byte[] fullBytes = Arrays.copyOf(oneTimePass, oneTimePass.length + certBytes.length);
                System.arraycopy(certBytes, 0, fullBytes, oneTimePass.length, certBytes.length);
                String fullString = DatatypeConverter.printBase64Binary(fullBytes);
                
                QRCodeWriter writer = new QRCodeWriter();
                Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hints.put(EncodeHintType.MARGIN, 0);
                BitMatrix matrix = writer.encode(fullString, BarcodeFormat.QR_CODE, 300, 300, hints);

                BufferedImage image = new BufferedImage(TAM_QRCODE, TAM_QRCODE, BufferedImage.TYPE_INT_RGB);
                image.createGraphics();

                Graphics2D graphics = (Graphics2D) image.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, TAM_QRCODE, TAM_QRCODE);
                graphics.setColor(Color.BLACK);

                for (int i = matrix.getTopLeftOnBit()[0]; i < TAM_QRCODE; i++) {
                    for (int j = matrix.getTopLeftOnBit()[1]; j < TAM_QRCODE; j++) {
                        if (matrix.get(i, j)) {
                            graphics.fillRect(i, j, 1, 1);
                        }
                    }
                }

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                
                Icon icon = new ImageIcon(image);
                
                JLabel iconLabel = new JLabel(icon);
                JPanel iconPanel = new JPanel(new GridBagLayout());
                iconPanel.add(iconLabel);

                JPanel mainPanel = new JPanel(new BorderLayout());
                JLabel label = new JLabel("Capture this image to pair with phone. Warning: This will never be shown again");
                Border paddingBorder = BorderFactory.createEmptyBorder(30,10,10,10);
                label.setBorder(paddingBorder);
                mainPanel.add(label);
                mainPanel.add(iconPanel, BorderLayout.NORTH);
                log.debug("Showing wallet qr panel");
                JOptionPane.showMessageDialog(null, mainPanel, "Two Factor", JOptionPane.PLAIN_MESSAGE);
                log.debug("Showed wallet qr panel");
                
                log.debug("Trying to create ECKey");
                RemoteECKey newKey = new RemoteECKey(alice, params, t.bobShare, t.publicKey, oneTimePass, keystore, KEYSTORE_PASSWORD);
                log.debug("Finished trying to create ECKey");
                String filename = Utils.bytesToHexString(newKey.getPubKeyHash());
                
                FileOutputStream fileOut = new FileOutputStream("/Users/hkalodner/btfa_work/" + filename + ".key");
    			ObjectOutputStream out = new ObjectOutputStream(fileOut);
    	    	out.writeObject(newKey);
    	    	out.close();
    	    	fileOut.close();
                log.debug("Created ECKey");
                
                newWallet.addKey(newKey);
                WalletData perWalletModelData = new WalletData();
                perWalletModelData.setWalletInfo(new WalletInfoData(newWalletFilename, newWallet, MultiBitWalletVersion.PROTOBUF));
                perWalletModelData.setWallet(newWallet);
                perWalletModelData.setWalletFilename(newWalletFilename);
                perWalletModelData.setWalletDescription(controller.getLocaliser().getString(
                        "createNewWalletSubmitAction.defaultDescription"));
                this.bitcoinController.getFileHandler().savePerWalletModelData(perWalletModelData, true);

                // Start using the new file as the wallet.
                this.bitcoinController.addWalletFromFilename(newWalletFile.getAbsolutePath());
                this.bitcoinController.getModel().setActiveWalletByFilename(newWalletFilename);
                controller.getModel().setUserPreference(BitcoinModel.GRAB_FOCUS_FOR_ACTIVE_WALLET, "true");

                // Save the user properties to disk.
                FileHandler.writeUserPreferences(this.bitcoinController);
                log.debug("User preferences with new wallet written successfully");

                // Backup the wallet and wallet info.
                BackupManager.INSTANCE.backupPerWalletModelData(bitcoinController.getFileHandler(), perWalletModelData);
                
                controller.fireRecreateAllViews(true);
                controller.fireDataChangedUpdateNow();
            }
        } catch (WalletLoadException e) {
        	e.printStackTrace();
            message = controller.getLocaliser().getString("createNewWalletAction.walletCouldNotBeCreated",
                    new Object[] { newWalletFilename, e.getMessage() });
            log.error(message);
            MessageManager.INSTANCE.addMessage(new Message(message));
            theWalletWasNotOpenedSuccessfully = true;
        } catch (WalletSaveException e) {
        	e.printStackTrace();
            message = controller.getLocaliser().getString("createNewWalletAction.walletCouldNotBeCreated",
                    new Object[] { newWalletFilename, e.getMessage() });
            log.error(message);
            MessageManager.INSTANCE.addMessage(new Message(message));
            theWalletWasNotOpenedSuccessfully = true;
        } catch (WalletVersionException e) {
        	e.printStackTrace();
            message = controller.getLocaliser().getString("createNewWalletAction.walletCouldNotBeCreated",
                    new Object[] { newWalletFilename, e.getMessage() });
            log.error(message);
            MessageManager.INSTANCE.addMessage(new Message(message));
            theWalletWasNotOpenedSuccessfully = true;
        } catch (IOException e) {
        	e.printStackTrace();
            message = controller.getLocaliser().getString("createNewWalletAction.walletCouldNotBeCreated",
                    new Object[] { newWalletFilename, e.getMessage() });
            log.error(message);
            MessageManager.INSTANCE.addMessage(new Message(message));
            theWalletWasNotOpenedSuccessfully = true;
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if (theWalletWasNotOpenedSuccessfully) {
            WalletData loopData = this.bitcoinController.getModel().getPerWalletModelDataByWalletFilename(newWalletFilename);
            if (loopData != null) {
                // Clear the backup wallet filename - this prevents it being automatically overwritten.
                if (loopData.getWalletInfo() != null) {
                    loopData.getWalletInfo().put(BitcoinModel.WALLET_BACKUP_FILE, "");
                }
            }
        }
*/
}
  
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
