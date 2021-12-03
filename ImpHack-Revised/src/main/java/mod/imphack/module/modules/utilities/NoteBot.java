package mod.imphack.module.modules.utilities;

import com.google.gson.*;
import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.setting.settings.StringSetting;
import mod.imphack.util.notebot.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.sound.midi.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//this module is just an experiment and is not used in the current version of the client
public class NoteBot extends Module {

	final ModeSetting mode = new ModeSetting("Mode", this, "Tune", "Tune", "Play", "Listen");
	final StringSetting file = new StringSetting("Filename", this, "example.mid");

	public NoteBot() {
		super("Note Bot", "Plays Note Blocks", Category.UTILITIES);

		addSetting(mode);
		addSetting(file);
	}

	public static Path musicFolder;

	public static ArrayList<NbNote> ToTune;

	@Override
	public void onEnable() {

		if (mc.player == null || mc.world == null) {
			this.toggle();
			return;
		}

		MinecraftForge.EVENT_BUS.register(this);

		ImpHackEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();

		NbMapper.MapInstruments();

		Path musicFile;
		if (mode.is("Tune")) {

			// TODO test

			NbMapper.GetInstrument(1);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(2);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(3);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(4);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(5);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(6);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(7);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(8);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(9);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(10);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(11);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(12);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(13);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(14);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(15);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(16);
			NbInstrument.DiscoverInstrument();

			NbMapper.GetInstrument(1);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(2);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(3);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(4);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(5);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(6);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(7);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(8);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(9);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(10);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(11);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(12);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(13);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(14);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(15);
			NbInstrument.TuneInstrument();

			NbMapper.GetInstrument(16);
			NbInstrument.TuneInstrument();

		} else if (mode.is("Play")) {
			musicFolder = Minecraft.getMinecraft().gameDir.toPath().resolve("imphack" + File.separator + "notebot");
			musicFile = Paths.get(musicFolder + File.separator + file.value);
			try {
				Files.createDirectories(musicFolder);
				if (!Files.exists(musicFile)) {
					Client.addChatMessage("could not find the specified file");
					this.toggle();
				} else {
					readMidi(musicFile.toFile());
					playMusic();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (mode.is("Listen")) {
			musicFolder = Minecraft.getMinecraft().gameDir.toPath().resolve("imphack" + File.separator + "notebot");
			musicFile = Paths.get(musicFolder + File.separator + file.value);
			try {
				Files.createDirectories(musicFolder);
				if (!Files.exists(musicFile)) {
					Client.addChatMessage("could not find the specified file");
					this.toggle();
				} else {
					readMidi(musicFile.toFile());
					listenMusic();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static final int NOTE_ON = 0x90;
	static final int SET_TEMPO = 0x51;
	static final int TIME_SIGNATURE = 0x58;
	static int PATCH_CHANGE = 0xC0;

	static Sequence seq;
	static int[] channelprograms;

	static String noteBotJson;

	private static void readMidi(File file) throws InvalidMidiDataException, IOException {
		seq = MidiSystem.getSequence(file);
		channelprograms = new int[30];

		JsonObject main = new JsonObject();
		main.addProperty("Name", file.getName().substring(0, file.getName().length() - 4));

		JsonObject data = new JsonObject();
		main.addProperty("Length", 0);
		main.add("Data", data);

		HashMap<Integer, JsonObject> jsonchannels = new HashMap <>();

		long maxstamp = 0;

		int timesignum;
		int timesigden;
		int mspqn = 500000;

		int res = seq.getResolution();

		int trackn = 0;
		for (Track track : seq.getTracks()) {
			for (int eventidx = 0; eventidx < track.size(); eventidx++) {
				MidiEvent event = track.get(eventidx);

				if (IsTimeSignature(event)) {
					MetaMessage mmsg = (MetaMessage) event.getMessage();

					byte[] timesig = mmsg.getMessage();

					timesignum = timesig[2];
					timesigden = timesig[3];

					// bpm = ( minuteinms / newMicrosecondsPerQuarterNote ) * ( denominator / 4.0f
					// );

					System.out.println("TIME_SIGNATURE Track: " + trackn + " msg: num: " + timesignum + " den: "
							+ timesigden + " Tick: " + event.getTick());
				} else if (IsTempoChange(event)) {
					MetaMessage mmsg = (MetaMessage) event.getMessage();

					// 500000
					mspqn = new BigInteger(mmsg.getData()).intValue();

					System.out.println("SET_TEMPO Track: " + trackn + " msg: " + mspqn + " Tick: " + event.getTick());
				} else if (IsNoteOn(event)) {
					ShortMessage smsg = (ShortMessage) event.getMessage();

					int note = smsg.getData1();
					long tick = event.getTick();
					int channel = smsg.getChannel();

					long time = (long) (((tick * (mspqn / res)) / 1000.0) + 0.5);

					note = note % 24;

					if (time >= maxstamp)
						maxstamp = time;

					if (jsonchannels.get(channel) == null) {
						JsonObject jsonchannel = new JsonObject();
						jsonchannels.put(channel, jsonchannel);

						jsonchannel.addProperty("OriginalInstrument", channelprograms[channel]);
						jsonchannel.addProperty("Instrument", "0");

						JsonArray jsonnotes = new JsonArray();
						jsonchannel.add("Notes", jsonnotes);
					}

					JsonObject element = new JsonObject();

					element.addProperty("Note", "" + note);
					element.addProperty("Tick", "" + time);

					jsonchannels.get(channel).getAsJsonArray("Notes").add(element);

					System.out.println("NOTE_ON Track: " + trackn + " Note: " + note + " Instrument: "
							+ channelprograms[channel] + " Channel: " + channel + " Tick: " + tick + " Time: " + time);
				} else if (IsPatchChange(event)) {
					ShortMessage smsg = (ShortMessage) event.getMessage();
					long tick = event.getTick();
					int channel = smsg.getChannel();
					int program = smsg.getData1();

					channelprograms[channel] = program + 1;

					System.out.println("PATCH_CHANGE Track: " + trackn + " Program: " + program + " Channel: " + channel
							+ " Tick: " + tick);
				}
			}

			trackn++;
		}

		int jsonchanneln = 1;
		for (Map.Entry<Integer, JsonObject> entryset : jsonchannels.entrySet()) {
			data.add("" + jsonchanneln, entryset.getValue());

			jsonchanneln++;
		}

		main.addProperty("Length", maxstamp);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		noteBotJson = gson.toJson(main);
	}

	private static boolean IsPatchChange(MidiEvent event) {
		if (!(event.getMessage() instanceof ShortMessage))
			return false;

		ShortMessage smsg = (ShortMessage) event.getMessage();

		return smsg.getStatus() >= 0xC0 && smsg.getStatus() <= 0xCF;
	}

	private static boolean IsTempoChange(MidiEvent event) {
		if (!(event.getMessage() instanceof MetaMessage))
			return false;

		MetaMessage mmsg = (MetaMessage) event.getMessage();

		return mmsg.getType() == SET_TEMPO;
	}

	private static boolean IsTimeSignature(MidiEvent event) {
		if (!(event.getMessage() instanceof MetaMessage))
			return false;

		MetaMessage mmsg = (MetaMessage) event.getMessage();

		return mmsg.getType() == TIME_SIGNATURE;
	}

	private static boolean IsNoteOn(MidiEvent event) {
		if (!(event.getMessage() instanceof ShortMessage))
			return false;

		ShortMessage smsg = (ShortMessage) event.getMessage();

		return smsg.getCommand() == NOTE_ON;
	}

	String name;
	long length;
	ArrayList<MusicChannel> channels;
	float progress;

	public static volatile boolean Running;

	public static volatile boolean Playing;
	public static volatile boolean Started;
	public static volatile Music PlayingMusic;

	public static volatile String LastNote;
	public static volatile Color LastNoteColor;

	public void playMusic() {
		// TODO
		// noteBotJson is the json we read here

		NbPlayer.PlayingMusic = new Music();
		NbPlayer.Started = false;
		NbPlayer.Playing = true;
	}

	public void listenMusic() {
		// TODO add listener
	}

	public static
	class MusicChannel {
		final int id;
		final int instrument;
		final int originalinstrument;
		final ArrayList<MusicNote> notes;

		public MusicChannel(int id, int instrument, int originalinstrument) {
			this.id = id;
			this.instrument = instrument;
			this.originalinstrument = originalinstrument;

			notes = new ArrayList <>();
		}

		public int GetInstrument() {
			return instrument;
		}

		public void AddNote(MusicNote note) {
			notes.add(note);
		}

		public void RemoveNextNote() {
			if (notes.size() > 0)
				notes.remove(0);
		}

		public boolean IsDone() {
			return notes.size() == 0;
		}

		public ArrayList<MusicNote> GetNextNotes() {
			ArrayList<MusicNote> ret = new ArrayList <>();
			if (notes.size() > 0) {
				int time;
				int i = 0;

				MusicNote note = notes.get(0);
				do {
					ret.add(note);
					time = note.GetTime();

					i++;
					if (i >= notes.size())
						break;

					note = notes.get(i);
				} while (note.GetTime() == time);
			}

			return ret;
		}

		public ArrayList<MusicNote> GetNotes() {
			return notes;
		}
	}

	public static
	class MusicNote {
		final int note;
		final int time;

		public MusicNote(int note, int time) {
			this.note = note;
			this.time = time;
		}

		public int GetNote() {
			return note;
		}

		public int GetTime() {
			return time;
		}
	}

	public static
	class Music {
		final String name;
		final long length;
		final ArrayList<MusicChannel> channels;
		float progress;

		public Music() {
			channels = new ArrayList <>();
			progress = 0;

			String content = noteBotJson;

			JsonParser jsonparser = new JsonParser();
			JsonObject mainjson = (JsonObject) jsonparser.parse(content);

			name = mainjson.get("Name").getAsString();
			length = mainjson.get("Length").getAsLong();

			for (Map.Entry<String, JsonElement> channelel : mainjson.getAsJsonObject("Data").entrySet()) {
				JsonObject channeljson = channelel.getValue().getAsJsonObject();

				int instrument = channeljson.get("Instrument").getAsInt();
				int originalinstrument = channeljson.get("OriginalInstrument").getAsInt();

				MusicChannel channel = new MusicChannel(Integer.parseInt(channelel.getKey()), instrument,
						originalinstrument);

				JsonArray notesjson = channeljson.getAsJsonArray("Notes");

				for (int i = 0; i < notesjson.size(); i++) {
					JsonElement noteel = notesjson.get(i);

					int note = noteel.getAsJsonObject().get("Note").getAsInt();
					int time = noteel.getAsJsonObject().get("Tick").getAsInt();

					channel.AddNote(new MusicNote(note, time));
				}

				channels.add(channel);
			}
		}

		public float GetProgress() {
			return progress;
		}

		public void CalculateProgress(long starttime, long currenttime) {
			progress = NbUtil.LongInterpolate(currenttime, starttime, starttime + length, 0.0f, 1.0f);
		}

		public ArrayList<MusicChannel> GetChannels() {
			return channels;
		}

		public String GetName() {
			return name;
		}

		public long GetLength() {
			return length;
		}
	}

	@SubscribeEvent
	public void OnNotePlayed(NoteBlockEvent event) {
		if (NbInstrument.Tunning) {
			if (NbInstrument.ToTune.size() == 0) {
				NbInstrument.CurrentTuneComplete = true;
				return;
			}

			if (event.getVanillaNoteId() == NbInstrument.ToTune.get(0).GetPitch())
				NbInstrument.CurrentTuneComplete = true;
		}
		if (NbInstrument.Discovering) {
			NbNote note = NbInstrument.ToDiscover.get(0);

			note.SetKnownPitch(event.getVanillaNoteId());

			if (event.getVanillaNoteId() == note.GetPitch())
				note.SetValidated(true);

			NbInstrument.CurrentDiscoveryComplete = true;
		}
	}

	long lsttime = 0;
	long starttime = 0;

	@SubscribeEvent
	public void OnTick(TickEvent.ClientTickEvent event) {
		if (NbInstrument.Tunning) {
			if (NbInstrument.ToTune.size() == 0) {
				NbInstrument.Tunning = false;
				NbInstrument.CurrentInstrument = -1;
				return;
			}

			NbNote note = NbInstrument.ToTune.get(0);

			if (!NbInstrument.CurrentTuneComplete) {

				if (System.currentTimeMillis() - lsttime >= 100) {
					NbUtil.RightClick(note.GetPosition());
					lsttime = System.currentTimeMillis();
				}
			} else {
				NbInstrument.ToTune.remove(0);
				NbInstrument.CurrentTuneComplete = false;
			}
		}

		if (NbInstrument.Discovering) {
			if (NbInstrument.ToDiscover.size() == 0) {
				NbInstrument.Discovering = false;
				NbInstrument.CurrentInstrument = -1;
				return;
			}

			NbNote note = NbInstrument.ToDiscover.get(0);

			if (!NbInstrument.CurrentDiscoveryComplete) {
				if (System.currentTimeMillis() - lsttime >= 100) {
					NbUtil.LeftClick(note.GetPosition());
					lsttime = System.currentTimeMillis();
				}
			} else {
				NbInstrument.ToDiscover.remove(0);
				NbInstrument.CurrentDiscoveryComplete = false;
			}
		}

	}
}
