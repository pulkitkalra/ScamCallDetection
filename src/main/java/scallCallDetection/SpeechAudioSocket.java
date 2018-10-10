package scallCallDetection;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

import profile.ProfileDTO;

/**
 * Recognize microphone input speech continuously using WebSockets.
 */
public class SpeechAudioSocket {
	public static BlockingQueue<String> textQueue; // change this.
	private ProfileDTO dto;
	
	public SpeechAudioSocket(ProfileDTO dto) {
		this.dto = dto;
		setTextQueue(new LinkedBlockingQueue<>());
	}
	/**
	 * Convert speech sample to string with IBM Watson SDK.
	 * This method is synchronized which allows safe thread management
	 *
	 * @param args the arguments
	 * @throws Exception
	 */
	public synchronized void convertSpeechToText() throws Exception {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("f3b5a97a-7c64-41ea-a126-9ff11e928349", "ElzlybghTyBP");

		// Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		int sampleRate = 16000;
		AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)) {
			System.out.println("Line not supported");
			System.exit(0);
		}

		TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();

		AudioInputStream audio = new AudioInputStream(line);

		RecognizeOptions options = new RecognizeOptions.Builder()
				.audio(audio)
				.interimResults(true)
				.timestamps(true)
				.wordConfidence(true)
				//.inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
				.contentType(HttpMediaType.AUDIO_RAW + ";rate=" + sampleRate)
				.build();

		service.recognizeUsingWebSocket(options, new BaseRecognizeCallback() {
			@Override
			public void onTranscription(SpeechRecognitionResults speechResults) {
				if (speechResults.getResults().get(0).isFinalResults()) {
					String transcriptTemp =  speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
					try {
						textQueue.put(transcriptTemp);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//this.notify();
					System.out.println("Final Res");
				}
				
				String transcript =  speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
				System.out.println(transcript);
				dto.setRecordingText(transcript);
			}
		});

		System.out.println("Listening to your voice for the next 10s...");
		Thread.sleep(30 * 1000);

		// closing the WebSockets underlying InputStream will close the WebSocket itself.
		line.stop();
		line.close();

		System.out.println("Fin.");
	}
	
	public Queue<String> getTextQueue() {
		return textQueue;
	}
	
	public void setTextQueue(BlockingQueue<String> blockingQueue) {
		SpeechAudioSocket.textQueue = blockingQueue;
	}
}
