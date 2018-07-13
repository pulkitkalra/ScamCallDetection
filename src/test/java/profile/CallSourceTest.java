package profile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import entities.Organisation;
import featureExtraction.CallFeatureExtraction;

class CallSourceTest {
	private CallFeatureExtraction extraction;

	@BeforeEach
	void setUp() throws Exception {
		extraction = new CallFeatureExtraction();
	}

	@Test
	void testCallSource() {
		CallSource source = new CallSourceImp();
		source.addOrganisation(Organisation.GOVERNMENT_ENTITY);
		source.addOrganisation(Organisation.IRS);

		CallProfile extractedProfile = extraction.getCallProfile();
		CallSource extractedSource = extractedProfile.getCallSource();

		assertAll("person",
			() -> assertTrue(extractedSource.getName().contains("James")),
			() -> assertTrue(extractedSource.getOrganisations().contains(Organisation.GOVERNMENT_ENTITY)),
			() -> assertTrue(extractedSource.getOrganisations().contains(Organisation.IRS))
		);
	}

}
