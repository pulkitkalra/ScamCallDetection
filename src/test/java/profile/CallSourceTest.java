package profile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import entities.Organisation;
import featureExtraction.CallFeatureExtraction;

@Disabled
class CallSourceTest {
	private CallFeatureExtraction extraction;

	@BeforeEach
	void setUp() throws Exception {
		extraction = new CallFeatureExtraction();
	}

	@Test
	void testCallSource() {
		CallSource source = new CallSourceImp();
		source.addOrganisation(Organisation.CRIMINAL_INVESTIGATION_DEPRATMENT);
		source.addOrganisation(Organisation.IRS);

		CallProfile extractedProfile = extraction.getCallProfile();
		CallSource extractedSource = extractedProfile.getCallSource();

		assertAll("person",
			() -> assertTrue(extractedSource.getName().contains("James")),
			() -> assertTrue(extractedSource.getOrganisations().contains(Organisation.CRIMINAL_INVESTIGATION_DEPRATMENT)),
			() -> assertTrue(extractedSource.getOrganisations().contains(Organisation.IRS))
		);
	}

}
