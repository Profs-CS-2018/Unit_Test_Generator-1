#include "TestHarness.h"

struct cplusplusFixture:testing::Test
{
	cplusplus *comp;

	cplusplusFixture()
	{
		comp = new cplusplus();
	}

	~cplusplusFixture()
	{
		delete comp;
	}
}