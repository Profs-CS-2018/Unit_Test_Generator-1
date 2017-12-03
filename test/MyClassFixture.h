#include "TestHarness.h"
#include "MyClass.h"

struct MyClassFixture:testing::Test
{
	MyClass *comp;

	MyClassFixture()
	{
		comp = new MyClass();
	}

	~MyClassFixture()
	{
		delete comp;
	}
}