#include "TestHarness.h"
#include "TestHarness.h"
#include "ExampleFixture.h"

struct ExampleTestFixture:testing::Test
{
	ExampleTest *comp;

	ExampleTestFixture()
	{
		comp = new ExampleTest();
	}

	~ExampleTestFixture()
	{
		delete comp;
	}
}